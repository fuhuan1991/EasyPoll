(function main() {
    var max_count = {};
    var poll_id = '';
    var progress_bar_list = document.querySelectorAll(".progress-bar");
    var option_counter_list = document.querySelectorAll(".counter");
    var option_map = {};
    var voterAlertString = '<div class="alert alert-info alert-dismissible fade show" role="alert">A user just voted!<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>';
    var connectionAlertString = '<div class="alert alert-warning alert-dismissible fade show" role="alert">Connection time out, please refresh. <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>';
    var retry = 5;

    for (bar of progress_bar_list) {
        let option_id = bar.getAttribute('option_id');
        option_map[option_id] = {
            bar: bar,
        };
    }

    for (counter of option_counter_list) {
        let option_id = counter.getAttribute('option_id');
        option_map[option_id].counter = counter;
    }

    renderData();

    // start SSE
    if (!!window.EventSource) {
      poll_id = document.querySelector('#poll_id').innerText;
      var source = new EventSource(`/sse/${poll_id}`);

      source.addEventListener('message', function(e) {
        console.log('a person just voted');
        var data = JSON.parse(e.data);
        renderData(data);
      }, false);

      source.addEventListener('open', function(e) {
        // Connection was opened.
        console.log('Connection was opened.');
      }, false);

      source.addEventListener('error', function(e) {
        console.log('Connection error. retry = ' + retry);
        if (retry-- > 0) {
          let alertDom = createElementFromHTML(connectionAlertString);
          document.querySelector("#alerts").appendChild(alertDom);
        } else {
          location.reload();
        }

      }, false);
    } else {
      // fallback to normal result page
      location.href = `/result/${poll_id}`;
    }

    document.querySelector('#refresh').addEventListener('click', () => {
        location.reload();
    });

    function renderData(data) {

        if (!!data) {
            for (let question of data.questions) {
                for (let option of question.options) {
                    let option_id = option.id;
                    let count = option.count;
                    option_map[option_id].bar.setAttribute('count', count);
                    option_map[option_id].counter.innerText = count;
                }
            }
            let alertDom = createElementFromHTML(voterAlertString);
            document.querySelector("#alerts").appendChild(alertDom);
            setTimeout(() => { $($('.alert')[0]).alert('close') }, 3000);
        }

        for (bar of progress_bar_list) {
            let question_id = bar.getAttribute('question_id');
            let count = bar.getAttribute('count');
            if (max_count[question_id] === undefined) {
                max_count[question_id] = count;
            } else {
                max_count[question_id] = Math.max(max_count[question_id], count);
            }
        }

        for (bar of progress_bar_list) {
            let question_id = bar.getAttribute('question_id');
            let count = bar.getAttribute('count');
            let max = Math.max(10, max_count[question_id]*1.2);
            let ratio = 100*count/max;
            bar.style.width = ratio + "%";
        }
    }

    function createElementFromHTML(htmlString) {
      var div = document.createElement('div');
      div.innerHTML = htmlString.trim();
      return div.firstChild;
    }

})()