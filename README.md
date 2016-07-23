#SW Maestro First Assignment

필수사항

1. 앱(게임)과 서버 통시하는 로직이 포함되어야함.
  * Android Client에서 Retrofit(ver 1.9)을 사용하여 AWS EC2 instance(Amazon Linux AMI)의 Rails와 통신하도록 구현함.

2. 클라이언트 게임은 게임첫 설치 시간. 접속시간. 접속후 게임종료시간. 마지막 플레이시간등이 DB에 저장되어야 함.
  * Game이 아닌 모바일 앱이므로 접속시간은 구현하지 않음. 다만 CMS에서 DB생성 및 업데이트 시간은 기록이 됨.

3. 페이스북, 트위터, 이메일등 타 서비스 연계가 1개이상 되어야 함.
  * Client에서 카카오 연동을 통해 컨텐츠를 카카오톡으로 전송이 가능

4. 앱스토어에 업로드 되어야함.
  * Google Play Store 등록 완료(https://play.google.com/store/apps/details?id=com.jeemyeong.toeatlist)

5. 서버 프로그램이 클라우드나 호스팅이 되는 서버에 배포가 되어야함.
  * AWS EC2 instance(Amazon Linux AMI)에서 Ruby on Rails와 Apache를 통해 배포 완료.
  (ip:52.78.99.238 / port:80)

6. 데이터는 DB에 저장되어 SQL을 통해서 조회 및 추가가 가능해야함.
  * Rails에서 SQLite3을 통해 DB를 관리하며, Android Client에서도 SQLite를 통해 LocalDATA.db로 로컬DB를 저장함.

7. 백엔드는 BaaS나 클라우드 제한 없으나 웹서비스 형태이어야 함.
  * AWS EC2 instance(Amazon Linux AMI) 클라우드 서버를 사용함.


가점 사항

1. 클라우드 이용시 가점.
  * AWS EC2 instance(Amazon Linux AMI) 클라우드 서버를 사용함.

2. 친구추가, 랭킹, 선물하기등의 기능 추가시 가점.
  * 게임이 아니기 때문에 친구추가는 없음. 카카오톡을 통해 친구에게 컨텐츠를 전송하는 것은 가능함.

3. Restful API 형태 설계 적용시 가점.
  * Client에서는 GET(http://52.78.99.238/foods.json) 으로 Contents를 내려받으며, POST(http://52.78.99.238/foods/upload) 를 통해 Contents 업로드 가능함.

4. push 구현 되었으면 가점.
  * Push는 아직 구현되지 않음.

5. 안드로이드와 아이폰 둘다 대응시 가점.
  * 아이폰은 아직 대응되지 않음.

6. DB와 연동되는 별도의 CMS(관리시스템)을 구축시 가점.
  * Rails Scaffold를 이용하여 CMS구축(http://52.78.99.238/foods)


#평가를 위한 보아야 할 부분
* Google Play Store: https://play.google.com/store/apps/details?id=com.jeemyeong.toeatlist

* Rails: https://github.com/jeemyeong/ToEatListrails/

 * Controller: https://github.com/jeemyeong/ToEatListrails/blob/master/app/controllers/foods_controller.rb
 
 * View: https://github.com/jeemyeong/ToEatListrails/blob/master/app/views/foods/index.html.erb

 * CMS: http://52.78.99.238/foods
     
 * DB Table Structure: https://github.com/jeemyeong/ToEatListrails/blob/master/db/migrate/20160719114019_create_foods.rb
 
 * Image Uploader: https://github.com/jeemyeong/ToEatListrails/blob/master/app/uploaders/image_file_uploader.rb
  * CarrierWave를 통한 이미지 업로드 구축
  * fog를 통하여 aws s3 storage에 이미지 저장
  * mini_magick을 통하여 이미지 400 * 400 (px)으로 저장함
 
* Android: https://github.com/jeemyeong/ToEatList

 * Controller: https://github.com/jeemyeong/ToEatList/tree/master/app/src/main/java/com/jeemyeong/toeatlist/Controller

 * View: https://github.com/jeemyeong/ToEatList/tree/master/app/src/main/java/com/jeemyeong/toeatlist/View
 
 * Model: https://github.com/jeemyeong/ToEatList/tree/master/app/src/main/java/com/jeemyeong/toeatlist/Model

 * Retrofit Interface : https://github.com/jeemyeong/ToEatList/blob/master/app/src/main/java/com/jeemyeong/toeatlist/ServerInterface.java 
 
 * Layout: https://github.com/jeemyeong/ToEatList/tree/master/app/src/main/res/layout
