if (window.console) {
    console.log("Welcome to your Play application's JavaScript!");
}

$(document).ready(function (e) {

    $('#button-white').click( function(e) {
        $('body').removeClass('black_background');
        $('body').addClass('white_background');
        $('.headline').removeClass('black_background');
        $('.headline').addClass('white_background');
    });

    $('#button-black').click( function(e) {
        $('body').addClass('black_background');
        $('body').removeClass('white_background');
        $('.headline').addClass('black_background');
        $('.headline').removeClass('white_background');
    });

    function onSignIn(googleUser) {
        window.location = "/main";
    }

});
