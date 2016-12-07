if (window.console) {
    console.log("Welcome to your Play application's JavaScript!");
}

function makeBlack(){
    $('.container').addClass('black_background');
    $('.container').removeClass('white_background');
    $('#button-black').addClass('active');
    $('#button-white').removeClass('active');
}

function makeWhite(){
    $('.container').removeClass('black_background');
    $('.container').addClass('white_background');
    $('#button-black').removeClass('active');
    $('#button-white').addClass('active');
}

