insert into USERS (USER_ID, CREATE_DATE, UPDATE_DATE, EMAIL, NICKNAME, IS_DELETED, PASSWORD, ROLES)
values (777, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616', 'Tester3@gmail.com', 'Tester3', false, 'password', 'USER');

insert into USERS (USER_ID, CREATE_DATE, UPDATE_DATE, EMAIL, NICKNAME, IS_DELETED, PASSWORD, ROLES)
values (1, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616', 'Tester1@gmail.com', 'Tester1', false, 'password', 'USER');

insert into USERS (USER_ID, CREATE_DATE, UPDATE_DATE, EMAIL, NICKNAME, IS_DELETED, PASSWORD, ROLES)
values (2, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616', 'Tester1@gmail.com', 'Tester2', false, 'password', 'USER');

insert into USERS (USER_ID, CREATE_DATE, UPDATE_DATE, EMAIL, NICKNAME, IS_DELETED, PASSWORD, ROLES)
values (3, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616', 'Tester1@gmail.com', 'Tester3', false, 'password', 'USER');

insert into USERS (USER_ID, CREATE_DATE, UPDATE_DATE, EMAIL, NICKNAME, IS_DELETED, PASSWORD, ROLES)
values (4, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616', 'Tester1@gmail.com', 'ADMIN', false, 'password', 'USER');

insert into chat_room (CHAT_ROOM_ID, NAME, CREATE_DATE, UPDATE_DATE)
values (2, '채팅방1', '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616');

insert into auction_room
(create_date, user_id, limit_of_enrollment, name,
 password, update_date, auction_room_id)
values ('2023-05-09 14:32:19.667616', 1, 99, '테스트_경매장', null,
        '2023-05-09 14:32:19.667616', 2);

insert into auction
(auction_room_id, auction_status, begin_date_time, bid_start_price, category,
 close_date_time, create_date, content, price, title, update_date, auction_id)
values (2, 'BEGAN_ENROLLMENT', '2023-05-01 14:32:19.667616', 25000, 'DIGITAL',
        '2025-05-01 14:32:19.667616', '2024-05-01 14:32:19.667616',
        '저렴하게 팔아요!', 50000, '슈퍼 맥북', '2023-05-01 14:32:19.667616', 2);

insert into auction_participation
(auction_room_id, create_date, update_date, user_id)
values (2, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616',
        1);

insert into auction_participation
(auction_room_id, create_date, update_date, user_id)
values (2, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616',
        2);

insert into auction_participation
(auction_room_id, create_date, update_date, user_id)
values (2, '2023-05-09 14:32:19.667616', '2023-05-09 14:32:19.667616',
        3);