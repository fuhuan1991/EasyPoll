(function main() {

    var poll_id = document.querySelector('#poll_id').innerHTML;

    // check cookie, if current user has voted before, show modal
    if (document.cookie.indexOf(poll_id + '=taken') >= 0) {
        $('#info_modal').modal({backdrop: 'static'});
    }

    var question_set = new Set();
    var question_eles = document.querySelectorAll('.question');
    for (ele of question_eles) {
        question_set.add(ele.getAttribute('id'));
    }

    function check() {
        var selected_set = new Set();
        var option_eles = document.querySelectorAll('.option');
        for (ele of option_eles) {
            if (ele.checked) selected_set.add(ele.getAttribute('name'));
        }
        if (selected_set.size === question_set.size) {
            document.querySelector('#alert').classList.remove("show");
            payload = [];
            for (ele of option_eles) {
                if (ele.checked) payload.push(ele.getAttribute('id'));
            }
            return payload;
        } else {
            document.querySelector('#alert').classList.add("show");
            return null;
        }
    }

    var submit_ele = document.querySelector('#submit');
    submit_ele.addEventListener('click', () => {
        payload = check();
        if (payload === null) return;

        fetch(window.location.origin + '/api/take_poll/' + poll_id,
            {
                 method: 'POST',
                 headers: {'Content-Type': 'application/json'},
                 body: JSON.stringify({optionIds: payload}),
            }
        ).then(
            (response) => {
                console.log(response);
                if (response.ok) {
                    document.cookie = poll_id + '=taken';
                    $('#success_modal').modal({backdrop: 'static'});
                } else {
                    $('#error_modal').modal({backdrop: 'static'});
                }
            },
            (e) => {
                console.log(e);
                $('#error_modal').modal({backdrop: 'static'});
            }
        )
    });
})()