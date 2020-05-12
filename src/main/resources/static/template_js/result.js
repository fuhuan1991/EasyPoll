(function main() {
    var max_count = {};

    var progress_bar_list = document.querySelectorAll(".progress-bar");
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
    document.querySelector('#refresh').addEventListener('click', () => {
        location.reload();
    });
})()