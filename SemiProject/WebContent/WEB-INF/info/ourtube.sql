drop table bookmark_member;
drop table bookmark_board;
drop table board_recommend;
drop table member;
drop sequence bookmark_member_seq;
drop sequence board_recommend_seq;
drop sequence bookmark_board_seq;
--멤버 테이블----------------------------------------------
create table member(
	id varchar2(20) primary key,
	password varchar2(20) not null,
	nick varchar2(20) not null
);
--멤버용 북마크 테이블------------------------------------------
create table bookmark_member(
	no number primary key,
	folder_name varchar2(30) not null,
	channel_name varchar2(50) not null,
	channel_url varchar2(100),
	id varchar2(20) not null,
	constraint bookmark_member_fk foreign key(id) references member(id)
);
create sequence bookmark_member_seq;
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
--아이디 등록-----------------------------------------------------------------
insert into member values('kgs','1','고보승');
--북마크 유저용 등록-------------------------------------------------------------
insert into bookmark_member values(bookmark_member_seq.nextval,'요리','백종원의 요리비책','https://www.youtube.com/channel/UCyn-K7rZLXjGl7VXGweIlcA','kgs');
insert into bookmark_member values(bookmark_member_seq.nextval,'요리','하루한끼 one meal a day','https://www.youtube.com/channel/UCPWFxcwPliEBMwJjmeFIDIg','kgs');
insert into bookmark_member values(bookmark_member_seq.nextval,'근력운동','강경원','https://www.youtube.com/channel/UCuwyPNJScQ5luAV7b8juFfg','kgs');
insert into bookmark_member values(bookmark_member_seq.nextval,'근력운동','양선수의 온라인PT','https://www.youtube.com/channel/UCLG1XzhSPuuJ6hqaHhQaFkA','kgs');
insert into bookmark_member values(bookmark_member_seq.nextval,'근력운동','트리거15초','https://www.youtube.com/channel/UCoyogo_Fg-Z5jDOQz6Rt6eA','kgs');
insert into bookmark_member values(bookmark_member_seq.nextval,'공부용 음악','우든체어 wooden chair','https://www.youtube.com/channel/UCHFxbyVOxEPA2CSPBVKjUvg','kgs');
insert into bookmark_member values(bookmark_member_seq.nextval,'공부용 음악','마인드피아노 MIND PIANO','https://www.youtube.com/channel/UCYMEXdU9oVJ6JUoyL8Fv06Q','kgs');
insert into bookmark_member values(bookmark_member_seq.nextval,'공부용 음악','Cold Water','https://www.youtube.com/channel/UCpi4NSNZrV3oBtgpdaEGeyw','kgs');
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
  select br.post_no, br.title, to_char(br.time_posted,'yyyy-mm-dd') as time_posted, br.hits, m.id, m.nick 
  from board_recommend br, member m 
  where m.id=br.id
  
  --getPostingByNo-------------------------------------
select br.title, to_char(br.time_posted,'YYYY.MM.DD  HH24:MI:SS') as time_posted
,br.content, br.hits, br.id, m.nick
 from board_recommend br, member m
  where br.id=m.id and br.post_no=1
  
  --updateHit---------------------------
  update board_recommend set hits=hits+1 where no=1