if (window.console) {
    console.log("Welcome to your Play application's JavaScript!");
};

$('#button-white').click( function() {
    $('body').removeClass('black_background');
    $('body').addClass('white_background');
    $('.headline').removeClass('black_background');
    $('.headline').addClass('white_background');
})

$('#button-black').click( function() {
    $('body').addClass('black_background');
    $('body').removeClass('white_background');
    $('.headline').addClass('black_background');
    $('.headline').removeClass('white_background');
})


