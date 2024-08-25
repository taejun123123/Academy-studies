$(function() {
    // .dataRow 클래스를 가진 요소를 클릭했을 때 실행되는 함수
    $(".dataRow").click(function() {
        // 클릭된 요소 내에서 .no 클래스를 가진 요소의 텍스트를 가져옴
        let no = $(this).find(".no").text();
        console.log(no);

        // 클릭된 요소가 특정 클래스를 가지고 있는지 확인
        if ($(this).hasClass("board")) {
            // 일반 게시판 요소를 클릭한 경우
            location = "/board/view.do?no=" + no + "&inc=1";
        } else if ($(this).hasClass("image")) {
            // 이미지 게시판 요소를 클릭한 경우
            location = "/image/view.do?no=" + no;
        } else if ($(this).hasClass("notice")) {
            // 공지사항 게시판 요소를 클릭한 경우
            location = "/notice/view.do?no=" + no;
        }
    });
});
