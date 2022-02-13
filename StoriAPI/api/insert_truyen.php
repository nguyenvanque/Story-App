<?php
include '../services/Truyen_service.php';

$service = new TruyenService();

$tentruyen=$_POST['tentruyen'];
$hinhtruyen=$_POST['hinhtruyen'];
$mota=$_POST['mota'];
$gia=$_POST['gia'];
$maloai=$_POST['maloai'];

$result= $service->insert_truyen($tentruyen,$hinhtruyen,$mota,$gia,$maloai);

echo json_encode(array( "status" => true,"message" => "Thêm thành công" ) );

?>


