//create by ganquanzhong

$(function() {
	$(".pay_item").click(function() {
		$(this).addClass('checked').siblings('.pay_item').removeClass('checked');
		var dataid = $(this).attr('data-id');
		$(".shang_payimg img").attr("src", "ds_images/" + dataid + ".png");
		$("#shang_pay_txt").text(dataid == "alipay" ? "支付宝" : "微信");
	});
});

function dashangToggle() {
	$(".hide_box").fadeToggle();
	$(".shang_box").fadeToggle();
}