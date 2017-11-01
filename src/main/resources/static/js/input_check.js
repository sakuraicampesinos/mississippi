    // 入力内容チェックのための関数
    function input_check(){
    var result = true;

    // エラー用装飾のためのクラスリセット
    $('#start').removeClass("inp_error");
    $('#end').removeClass("inp_error");
    $('#title').removeClass("inp_error");
    $('#content').removeClass("inp_error");

    // 入力エラー文をリセット
    $("#start_error").empty();
    $("#end_error").empty();
    $("#title_error").empty();
    $("#content_error").empty();

    // 入力内容セット
    var start   = $("#start").val();
    var end   = $("#end").val();
    var title   = $("#title").val();
    var content   = $("#content").val();

    if(!title){
      $("#title_error").html("<i class='fa fa-exclamation-circle'></i> 必須入力です");
      $("#title").addClass("inp_error");
      result = false;
    }

    if(!content){
      $("#content_error").html("<i class='fa fa-exclamation-circle'></i> 必須入力です");
      $("#content").addClass("inp_error");
      result = false;
    }

    if(!start.match(/^\d{2}\:\d{2}$/)){
        $("#start_error").html("<i class='fa fa-exclamation-circle'></i> HH:mmの形式で入力してください");
        $("#start").addClass("inp_error");
        result = false;
      } else {
        var vHour = start.substr(0,2) - 0;
        var vMinutes = start.substr(3,2) - 0;

        if(vHour >= 0 && vHour > 23 || vMinutes >= 0 && vMinutes > 59){
          $("#start_error").html("<i class='fa fa-exclamation-circle'></i> 正しい時刻を入力してください");
          $("#start").addClass("inp_error");
             return false;
        }
      }

    if(!end.match(/^\d{2}\:\d{2}$/)){
        $("#end_error").html("<i class='fa fa-exclamation-circle'></i> HH:mmの形式で入力してください");
        $("#end").addClass("inp_error");
        result = false;
      } else {
        var vHour = end.substr(0,2) - 0;
        var vMinutes = end.substr(3,2) - 0;

        if(vHour >= 0 && vHour > 23 || vMinutes >= 0 && vMinutes > 59){
          $("#end_error").html("<i class='fa fa-exclamation-circle'></i> 正しい時刻を入力してください");
          $("#end").addClass("inp_error");
             return false;
        }
      }

    return result;
}