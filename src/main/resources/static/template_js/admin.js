(function main() {
    var poll_id = document.getElementById("poll_id").innerHTML;
    var admin_address = window.location.host + "/admin/" + poll_id;
    var result_address = window.location.host + "/resultRealTime/" + poll_id;
    var poll_address = window.location.host + "/poll/" + poll_id;

    document.getElementById("admin_address").value = admin_address;
    document.getElementById("poll_address").value = poll_address;
    document.getElementById("result_address").value = result_address;

    function copy(id) {
        var copyText = document.getElementById(id);
        copyText.select();
        copyText.setSelectionRange(0, 99999);
        document.execCommand("copy");
    }

    function goto(action) {
        window.location.assign("/" + action + "/" + poll_id);
    }

    document.getElementById("admin_copy").addEventListener("click", copy.bind(this, "admin_address"));
    document.getElementById("poll_copy").addEventListener("click", copy.bind(this, "poll_address"));
    document.getElementById("result_copy").addEventListener("click", copy.bind(this, "result_address"));

    document.getElementById("admin_goto").addEventListener("click", goto.bind(this, "admin"));
    document.getElementById("poll_goto").addEventListener("click", goto.bind(this, "poll"));
    document.getElementById("result_goto").addEventListener("click", goto.bind(this, "resultRealTime"));
})()