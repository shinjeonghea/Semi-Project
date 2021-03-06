drop table channel_member;
drop table bookmark_folder;
drop table bookmark_board;
drop table board_recommend;
drop table member;

drop sequence board_recommend_seq;
drop sequence bookmark_board_seq;
drop sequence bookmark_folder_seq;
drop sequence channel_member_seq;

<<<<<<< HEAD
DELETE FROM channel_member where folder_no='6' and channel_name='MBCNEWS';
DELETE FROM bookmark_folder where id='kgs' and folder_name='근력운동';
DELETE FROM bookmark_folder where id='kgs' and folder_name='공부용 음악';
=======
>>>>>>> refs/remotes/origin/main
------------------------------------------------------------------------------------------------
--각 테이블 select로 확인--------------------------------------------
select * from channel_member;
select * from bookmark_folder;
select * from bookmark_board;
select * from board_recommend;
select * from member;

------------------------------------------------------------------------------------------------
--멤버 테이블----------------------------------------------
create table member(
	id varchar2(20) primary key,
	password varchar2(20) not null,
	nick varchar2(20) not null
);

--멤버용 북마크 테이블------------------------------------------
create table bookmark_folder(
folder_no number primary key,
id varchar2(20) not null,
folder_name varchar2(100) not null,
<<<<<<< HEAD
constraint bookmark_folder_fk 
foreign key(id) 
references member(id)
);

ALTER TABLE bookmark_folder ADD UNIQUE (id,folder_name);
create sequence bookmark_folder_seq;

--채널 멤버 테이블 생성-------------------------------------------
create table channel_member(
	no number primary key,
	folder_no number not null,
	channel_name varchar2(50) not null,
	channel_url varchar2(100),
	constraint channel_member_fk 
	foreign key(folder_no) 
	references bookmark_folder(folder_no)
	on delete cascade
=======
constraint bookmark_folder_fk foreign key(id) references member(id)
>>>>>>> refs/remotes/origin/main
);
<<<<<<< HEAD

=======
ALTER TABLE bookmark_folder ADD UNIQUE (id,folder_name);
create sequence bookmark_folder_seq;

--채널 멤버 테이블 생성-------------------------------------------
create table channel_member(
   no number primary key,
   folder_no number not null,
   channel_name varchar2(50) not null,
   channel_url varchar2(100),
   constraint channel_member_fk 
   foreign key(folder_no) 
   references bookmark_folder(folder_no)
   on delete cascade
);
>>>>>>> refs/remotes/origin/main
create sequence channel_member_seq;
--추천보드 테이블---------------------------------------------------
create table board_recommend(
	post_no number primary key,
	title varchar2(300) not null,
	content clob not null,
	time_posted date not null,
	hits number default 0,
	id varchar2(100) not null,
	constraint board_recommend_fk foreign key(id) references member(id)
);

create sequence board_recommend_seq;
--북마크 게시판용 테이블-----------------------------------------------------------
create table bookmark_board(
	no number primary key,
	folder_name varchar2(100) not null,
	channel_name varchar2(100) not null,
	channel_url varchar2(100),
	post_no number not null,
	constraint bookmark_board_fk foreign key(post_no) references board_recommend(post_no)
);

create sequence bookmark_board_seq;

-------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------
-------------------------------------------------------------------------------------
--아이디 등록-----------------------------------------------------------------------
insert into member values('kgs','1','고보승');
insert into member values('java','a','김수권');

--북마크 폴더 등록---------------------------------------------------------------
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '요리');
<<<<<<< HEAD
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '사랑');
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '근력운동');
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '공부용 음악');
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'java', '요리');
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'java', '운동');

select * from bookmark_folder;

--북마크 폴더 업데이트---
update bookmark_folder set folder_name='사랑' where id='kgs' and folder_name='요리'
=======
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '요리'); -- 여기서 에러가 뜨면 유니크 성공
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '근력운동');
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'kgs', '공부용 음악');
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'java', '요리');
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'java', '요리'); -- 이것도 안되는게 정상
insert into bookmark_folder values(bookmark_folder_seq.nextval, 'java', '운동');

select * from bookmark_folder;

>>>>>>> refs/remotes/origin/main

--북마크 유저용 등록-------------------------------------------------------------
insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='kgs' and folder_name='요리'),
'백종원의 요리비책', 'https://www.youtube.com/channel/UCyn-K7rZLXjGl7VXGweIlcA');

insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='kgs' and folder_name='요리'),
'하루한끼 one meal a day', 'https://www.youtube.com/channel/UCPWFxcwPliEBMwJjmeFIDIg');


insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='kgs' and folder_name='근력운동'),
'양선수의 온라인PT', 'https://www.youtube.com/channel/UCLG1XzhSPuuJ6hqaHhQaFkA');

insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='kgs' and folder_name='근력운동'),
'트리거15초', 'https://www.youtube.com/channel/UCoyogo_Fg-Z5jDOQz6Rt6eA');

insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='kgs' and folder_name='공부용 음악'),
'우든체어 wooden chair', 'https://www.youtube.com/channel/UCHFxbyVOxEPA2CSPBVKjUvg');

insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='kgs' and folder_name='공부용 음악'),
'마인드피아노 MIND PIANO', 'https://www.youtube.com/channel/UCHFxbyVOxEPA2CSPBVKjUvg');

insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='kgs' and folder_name='공부용 음악'),
'Cold Water', 'https://www.youtube.com/channel/UCpi4NSNZrV3oBtgpdaEGeyw');

-- 이건 안되는게 정상 ('근력운동' 폴더 생성 안해서 안들어가야됨)
insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='java' and folder_name='근력운동'),
'강경원', 'https://www.youtube.com/channel/UCuwyPNJScQ5luAV7b8juFfg');

<<<<<<< HEAD
DELETE FROM bookmark_folder where id='kgs' and folder_name='요리';
DELETE FROM channel_member where channel_name='백종원의 요리비책';

select * from BOOKMARK_FOLDER;
select * from CHANNEL_MEMBER;
update bookmark_folder set folder_name='맛집' where id='kgs' and folder_name='사랑';
=======
select * from CHANNEL_MEMBER;

>>>>>>> refs/remotes/origin/main

--추천글 등록, 추천글용 폴더 등록---------------------------------------------
insert into board_recommend(post_no,title,content,time_posted,id)
values(board_recommend_seq.nextval,'title test1','content test1',sysdate,'kgs');

insert into bookmark_board(no,folder_name,channel_name,channel_url,post_no)
values(bookmark_board_seq.nextval,'근력운동','강경원','https://www.youtube.com/channel/UCuwyPNJScQ5luAV7b8juFfg','1');

insert into bookmark_board(no,folder_name,channel_name,channel_url,post_no)
values(bookmark_board_seq.nextval,'근력운동','양선수의 온라인PT','https://www.youtube.com/channel/UCLG1XzhSPuuJ6hqaHhQaFkA','1');

insert into bookmark_board(no,folder_name,channel_name,channel_url,post_no)
values(bookmark_board_seq.nextval,'근력운동','트리거15초','https://www.youtube.com/channel/UCoyogo_Fg-Z5jDOQz6Rt6eA','1');

--select-----------------------------------------------------

select * from member;
select * from bookmark_member;
select * from board_recommend;
select * from bookmark_board;


---------------------------------------------------------------------------------------------------------------------------------------------
--이 밑부분부터는 각자 참고해서 수정해서 사용-------------------------------------------------------------------
---------------------------------------------------------------------------------------------------------------------------------------------
select folder_name, channel_name, channel_url from bookmark_member where id='kgs' order by folder_name asc;

select distinct folder_name from bookmark_member where id='kgs';
--유저용 북마크 가져오기-------------------------------------------------------------------
select b.folder_name, b.channel_name, b.channel_url
from bookmark_member b, member m
where b.id=m.id and m.id='kgs'

--보드용 북마크 가져오기---------------------------------------------------------
select bb.folder_name, bb.channel_name, bb.channel_url
from bookmark_board bb, board_recommend br
where bb.post_no=br.post_no and br.post_no='1'

--likes 가져오기
select l.likes 
from board b, likes l
where b.post_no=l.post_no and b.post_no='1'

--list 용 postvo 가져오기----------------------------------
select br.post_no, br.title, 
to_char(br.time_posted,'yyyy-mm-dd') as time_posted, br.hits, m.id, m.nick 
from board_recommend br, member m 
where m.id=br.id
--detailPost 용 postvo 가져오기
--1. 북마크 가져오기
select bb.folder_name, bb.channel_name, bb.channel_url
from board_recommend br, bookmark_board bb
where br.post_no=bb.post_no and br.post_no='1'
--2. 내용
select br.post_no, br.title, br.content,
to_char(br.time_posted,'yyyy-mm-dd') as time_posted, br.hits, m.id, m.nick
from board_recommend br, member m
where m.id=br.id and br.post_no='1'

--글 삭제------------------------------------------------------
--board 테이블에서 삭제
delete
from board_recommend
where post_no='2'
--bookmark_board 테이블에서 삭제
delete
from bookmark_board
where post_no='2'

 
--getPostingList()---------------------
select br.post_no, br.title, to_char(br.time_posted,'yyyy-mm-dd') as time_posted, br.hits, m.nick 
from board_recommend br, member m 
where m.id=br.id

select * from MEMBER;
select * from BOARD_RECOMMEND;

select br.post_no, br.title, m.nick, to_char(br.time_posted, 'yyyy-mm-dd')as time_posted, br.hits
from member m, BOARD_RECOMMEND br
where m.id = br.id;

--getPostingByNo-------------------------------------
select br.title, to_char(br.time_posted,'YYYY.MM.DD  HH24:MI:SS') as time_posted
,br.content, br.hits, br.id, m.nick
from board_recommend br, member m
where br.id=m.id and br.post_no=1
  
<<<<<<< HEAD
  --updateHit---------------------------
  update board_recommend set hits=hits+1 where no=1
  
  
  delete 
  from bookmark_folder bf, channel_member cm
  where bf.folder_no=cm.folder_no and id=? and folder_no=?
=======

select * from MEMBER;
--updateHit---------------------------
update board_recommend set hits=hits+1 where no=1

-- getTotalPostCount ----------------------------------- 
select count(*) from board_recommend;

--paging을 위한 SQL문------------------------------------------------------
select rnum, br.post_no, br.title, time_posted, br.hits, m.nick 
from (
	select  row_number() over(order by post_no desc) as rnum, br.post_no, br.title, to_char(br.time_posted,'yyyy-mm-dd') as time_posted, br.hits, br.id
	from board_recommend br
) br, member m
where m.id=br.id AND rnum BETWEEN 1 AND 5;


-- 추천글 등록 -------------------------------------
select * from channel_member;
select * from bookmark_folder;
select * from bookmark_board;
select * from board_recommend;
select * from member;

insert into member values('IU', '1234', '아이유');
insert into member(id, password, nick) values('kmk', '5678', '민콩이');

insert into bookmark_board(no,folder_name,channel_name,channel_url,post_no)
values(bookmark_board_seq.nextval,'근력운동','트리거15초','https://www.youtube.com/channel/UCoyogo_Fg-Z5jDOQz6Rt6eA','1');

insert into board_recommend(post_no, title,content,time_posted,id)
values(board_recommend_seq.nextval,'이거 진짜 ㄹㅇ 추천함','진짜 너무 좋다 이 플레이리스트 개쩔어 갬성 오져 진짜 와 박수를 치고싶다 후..', sysdate, 'kmk');

insert into board_recommend(post_no,title,content,time_posted,id)
values(board_recommend_seq.nextval,'ㅊㅊ','ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎ', sysdate, 'IU');

insert into board_recommend(post_no,title,content,time_posted,id)
values(board_recommend_seq.nextval,'진짜 강추','ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅋㅋㅋㅇㅇㅋㅋㅋㅎㅎㅋㅎㅎ', sysdate, 'kgs');

insert into board_recommend(post_no,title,content,time_posted,id)
values(board_recommend_seq.nextval,'진짜 강추','ㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅎㅋㅋㅋㅇㅇㅋㅋㅋㅎㅎㅋㅎㅎ', sysdate, 'java');

--------------------------------------------
select * from channel_member;
select * from bookmark_folder;
-- 즐겨찾기에 해당하는 채널 찾기 (id를 구별할 수 없음.)------------------------------------------------
select bf.folder_name, cm.channel_name, cm.channel_url
from bookmark_folder bf, channel_member cm
where bf.folder_no=cm.folder_no and bf.folder_name='요리';

-- 즐겨찾기에 해당하는 채널 찾기 (id를 구별할 수 있음.)------------------------------------------------
select bf.folder_name, bf.folder_no, cm.channel_name, cm.channel_url
from bookmark_folder bf, channel_member cm
where bf.folder_no=cm.folder_no and bf.folder_name='요리' and bf.id='java';

insert into channel_member values(channel_member_seq.nextval,
(select folder_no from bookmark_folder where id='java' and folder_name='요리'),
'백종원의 요리비책', 'https://www.youtube.com/channel/UCyn-K7rZLXjGl7VXGweIlcA');


-- 글쓰기시에 bookmark_board에 insert함. ----------------------------------------------------------------------------------
select * from bookmark_board;
select * from bookmark_folder;
select * from board_recommend;
select * from channel_member;
select * from member;
	
	-- 즐겨찾기에 해당하는 채널 찾기
		select bf.folder_name, bf.folder_no, cm.channel_name, cm.channel_url
		from bookmark_folder bf, channel_member cm
		where bf.folder_no=cm.folder_no and bf.folder_name='요리' and bf.id='java';
		
	-- 다른 예시
		select bf.folder_name, bf.folder_no, cm.channel_name, cm.channel_url
		from bookmark_folder bf, channel_member cm
		where bf.folder_no=cm.folder_no and bf.folder_name='근력운동' and bf.id='kgs';
		
	-- 이거 이상함
	insert into bookmark_board (no,folder_name,channel_name,channel_url,post_no)
	values(
		bookmark_board_seq.nextval,
		(	select bf.folder_name
			from bookmark_folder bf, channel_member cm
			where bf.folder_no=cm.folder_no and bf.folder_name='근력운동' and bf.id='kgs'
		), 
		(
			select cm.channel_name
			from bookmark_folder bf, channel_member cm
			where bf.folder_no=cm.folder_no and bf.folder_name='근력운동' and bf.id='kgs'),
		(
			select cm.channel_url
			from bookmark_folder bf, channel_member cm
			where bf.folder_no=cm.folder_no and bf.folder_name='근력운동' and bf.id='kgs'
		),
		8
	);

	-- ORA-01427: single-row subquery returns more than one row
	insert into bookmark_board (no,folder_name,channel_name,channel_url,post_no)
	values(
		bookmark_board_seq.nextval,
		(	select bf.folder_name
			from bookmark_folder bf, channel_member cm
			where bf.folder_no=cm.folder_no and bf.folder_name='근력운동' and bf.id='kgs'
		), 
		(
			select cm.channel_name
			from bookmark_folder bf, channel_member cm
			where bf.folder_no=cm.folder_no and bf.folder_name='근력운동' and bf.id='kgs'),
		(
			select cm.channel_url
			from bookmark_folder bf, channel_member cm
			where bf.folder_no=cm.folder_no and bf.folder_name='근력운동' and bf.id='kgs'
		),
		8
	);







--고보승님(kgs) 폴더 확인-------------------------------------
select bf.folder_name, bf.folder_no, cm.channel_name, cm.channel_url
from bookmark_folder bf, channel_member cm
where bf.folder_no=cm.folder_no and bf.id='kgs';


-- 
select folder_name, channel_name, channel_url from bookmark_board where post_no = 48;


-- 확인
select * from bookmark_board where post_no=61;

select distinct folder_name from bookmark_board where post_no=61;
select folder_name, channel_name, channel_url from bookmark_board where post_no=69;

select * from MEMBER;

select folder_name, channel_name, channel_url from bookmark_board where post_no=65;

select folder_name from BOOKMARK_FOLDER where id='kgs';

select count(*) from BOOKMARK_FOLDER where id='kgs' and folder_name='요리';

select count(*) from BOOKMARK_FOLDER where id='kgs' and folder_name='요리121212';


>>>>>>> refs/remotes/origin/main
