// 'use strict';
//
// // document.write("<script src='jquery-3.6.1.js'></script>")
// document.write("<script\n" +
//     "  src=\"https://code.jquery.com/jquery-3.6.1.min.js\"\n" +
//     "  integrity=\"sha256-o88AwQnZB+VDvE9tvIXrMQaPlFFSUTR+nldQm1LuPXQ=\"\n" +
//     "  crossorigin=\"anonymous\"></script>")
//
// let usernamePage = document.querySelector('#username-page');
// let chatPage = document.querySelector('#chat-page');
// let usernameForm = document.querySelector('#usernameForm');
// let messageForm = document.querySelector('#messageForm');
// let messageInput = document.querySelector('#message');
// let messageArea = document.querySelector('#messageArea');
// let connectingElement = document.querySelector('.connecting');
//
// let stompClient = null;
// let username = null;
//
// let colors = [
//     '#2196F3', '#32c787', '#00BCD4', '#ff5652',
//     '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
// ];
//
// // roomId 파라미터 가져오기
// const url = new URL(location.href).searchParams;
// const roomId = url.get('roomId');
//
// function connect(event){
//
//     username = document.querySelector('#name').value.trim();
//
//     // username 중복 확인
//     isDuplicateName();
//
//     // usernamePage 에 hidden 속성 추가해서 가리고 chatPage를 등장시킴
//     usernamePage.classList.add('hidden');
//     chatPage.classList.remove('hidden');
//
//     // 연결하고자하는 Socket 의 endPoint
//     let socket = new SockJS('/ws-stomp');
//     stompClient = Stomp.over(socket);
//
//     stompClient.connect({},onConnected,onError);
//
//     event.preventDefault();
// }
//
// function onConnected() {
//
//     // sub 할 url => /sub/chat/room/roomId 로 구독한다
//     stompClient.subscribe('/sub/chat/room/' + roomId, onMessageReceived);
//
//     // 서버에 username 을 가진 유저가 들어왔다는 것을 알림
//     // /pub/chat/enterUser 로 메시지를 보냄
//     stompClient.send("/pub/chat/enterUser",
//         {},
//         JSON.stringify({
//             "roomId": roomId,
//             sender: username,
//             type: 'ENTER'
//         })
//     )
//
//     connectingElement.classList.add('hidden');
//
// }
//
// // 유저 닉네임 중복 확인
// function isDuplicateName() {
//
//     $.ajax({
//         type: "GET",
//         url: "/chat/duplicateName",
//         data: {
//             "username": username,
//             "roomId": roomId
//         },
//         success: function (data) {
//             console.log("함수 동작 확인 : " + data);
//
//             username = data;
//         }
//     })
//
// }
//
// // 유저 리스트 받기
// // ajax 로 유저 리스를 받으며 클라이언트가 입장/퇴장 했다는 문구가 나왔을 때마다 실행된다.
// function getUserList() {
//     const $list = $("#list");
//
//     $.ajax({
//         type: "GET",
//         url: "/chat/userlist",
//         data: {
//             "roomId": roomId
//         },
//         success: function (data) {
//             let users = "";
//             for (let i = 0; i < data.length; i++) {
//                 //console.log("data[i] : "+data[i]);
//                 users += "<li class='dropdown-item'>" + data[i] + "</li>"
//             }
//             $list.html(users);
//         }
//     })
// }
//
// function onError(error) {
//     connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
//     connectingElement.style.color = 'red';
// }
//
// // 메시지 전송때는 JSON 형식을 메시지를 전달한다.
// function sendMessage(event) {
//     let messageContent = messageInput.value.trim();
//
//     if (messageContent && stompClient) {
//         let chatMessage = {
//             "roomId": roomId,
//             sender: username,
//             message: messageInput.value,
//             type: 'TALK'
//         };
//
//         stompClient.send("/pub/chat/sendMessage", {}, JSON.stringify(chatMessage));
//         messageInput.value = '';
//     }
//     event.preventDefault();
// }
//
// // 메시지를 받을 때도 마찬가지로 JSON 타입으로 받으며,
// // 넘어온 JSON 형식의 메시지를 parse 해서 사용한다.
// function onMessageReceived(payload) {
//     //console.log("payload 들어오냐? :"+payload);
//     let chat = JSON.parse(payload.body);
//
//     let messageElement = document.createElement('li');
//
//     if (chat.type === 'ENTER') {  // chatType 이 enter 라면 아래 내용
//         messageElement.classList.add('event-message');
//         chat.content = chat.sender + chat.message;
//         getUserList();
//
//     } else if (chat.type === 'LEAVE') { // chatType 가 leave 라면 아래 내용
//         messageElement.classList.add('event-message');
//         chat.content = chat.sender + chat.message;
//         getUserList();
//
//     } else { // chatType 이 talk 라면 아래 내용
//         messageElement.classList.add('chat-message');
//
//         let avatarElement = document.createElement('i');
//         let avatarText = document.createTextNode(chat.sender[0]);
//         avatarElement.appendChild(avatarText);
//         avatarElement.style['background-color'] = getAvatarColor(chat.sender);
//
//         messageElement.appendChild(avatarElement);
//
//         let usernameElement = document.createElement('span');
//         let usernameText = document.createTextNode(chat.sender);
//         usernameElement.appendChild(usernameText);
//         messageElement.appendChild(usernameElement);
//     }
//
//     let contentElement = document.createElement('p');
//
//     // 만약 s3DataUrl 의 값이 null 이 아니라면 => chat 내용이 파일 업로드와 관련된 내용이라면
//     // img 를 채팅에 보여주는 작업
//     if(chat.s3DataUrl != null){
//         let imgElement = document.createElement('img');
//         imgElement.setAttribute("src", chat.s3DataUrl);
//         imgElement.setAttribute("width", "300");
//         imgElement.setAttribute("height", "300");
//
//         let downBtnElement = document.createElement('button');
//         downBtnElement.setAttribute("class", "btn fa fa-download");
//         downBtnElement.setAttribute("id", "downBtn");
//         downBtnElement.setAttribute("name", chat.fileName);
//         downBtnElement.setAttribute("onclick", `downloadFile('${chat.fileName}', '${chat.fileDir}')`);
//
//
//         contentElement.appendChild(imgElement);
//         contentElement.appendChild(downBtnElement);
//
//     }else{
//         // 만약 s3DataUrl 의 값이 null 이라면
//         // 이전에 넘어온 채팅 내용 보여주기기
//         let messageText = document.createTextNode(chat.message);
//         contentElement.appendChild(messageText);
//     }
//
//     messageElement.appendChild(contentElement);
//
//     messageArea.appendChild(messageElement);
//     messageArea.scrollTop = messageArea.scrollHeight;
// }
//
// function getAvatarColor(messageSender) {
//     let hash = 0;
//     for (let i = 0; i < messageSender.length; i++) {
//         hash = 31 * hash + messageSender.charCodeAt(i);
//     }
//
//     let index = Math.abs(hash % colors.length);
//     return colors[index];
// }
//
// usernameForm.addEventListener('submit', connect, true)
// messageForm.addEventListener('submit', sendMessage, true)