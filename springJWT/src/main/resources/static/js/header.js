document.addEventListener("DOMContentLoaded", function() {
    const menuItems = document.querySelectorAll(".nav-links li a");
    let sidebar = document.querySelector(".sidebar");
    let sidebarBtn = document.querySelector(".sidebarBtn");
    let homeSection = document.querySelector(".home-section");
    let nav = document.querySelector("nav");

    menuItems.forEach(item => {
        item.addEventListener("click", function() {
            // 모든 메뉴에서 active 클래스를 제거합니다.
            menuItems.forEach(link => link.classList.remove("active"));
            // 클릭된 메뉴에 active 클래스를 추가합니다.
            this.classList.add("active");
        });
    });

    if (sidebarBtn) {
        sidebarBtn.onclick = function() {
            sidebar.classList.toggle("active");

            if(sidebar.classList.contains("active")){
                homeSection.style.width = "calc(100% - 60px)";
                homeSection.style.left = "60px";
                nav.style.width = "calc(100% - 60px)";
                nav.style.left = "60px";
            } else {
                homeSection.style.width = "calc(100% - 240px)";
                homeSection.style.left = "240px";
                nav.style.width = "calc(100% - 240px)";
                nav.style.left = "240px";
            }
        }
    }
    $("#bagitBtn").click(function () {
        $.post("/bagit/create", null, function (res){
            alert(res);
        })
    });
});