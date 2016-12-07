if (window.console) {
    console.log("Welcome to your Play application's JavaScript!");
}

function makeBlack(){
    $('body').addClass('black_background');
    $('body').removeClass('white_background');
    $('.headline').addClass('black_background');
    $('.headline').removeClass('white_background');
    $('#button-black').addClass('active');
    $('#button-white').removeClass('active');
}

function makeWhite(){
    $('body').removeClass('black_background');
    $('body').addClass('white_background');
    $('.headline').removeClass('black_background');
    $('.headline').addClass('white_background');
    $('#button-black').removeClass('active');
    $('#button-white').addClass('active');
}

