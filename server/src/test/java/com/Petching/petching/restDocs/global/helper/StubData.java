package com.Petching.petching.restDocs.global.helper;

import com.Petching.petching.board.dto.BoardDto;
import com.Petching.petching.board.entity.Board;
import com.Petching.petching.carepost.dto.CarePostDto;
import com.Petching.petching.carepost.entity.CarePost;
import com.Petching.petching.comment.dto.CommentDto;
import com.Petching.petching.comment.entity.Comment;
import com.Petching.petching.global.aws.s3.dto.S3FileDto;
import com.Petching.petching.user.dto.UserPatchDto;
import com.Petching.petching.user.dto.UserPostDto;
import com.Petching.petching.user.dto.UserResponseDto;
import com.Petching.petching.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.*;


public class StubData {

    public static class MockComment{

        private static Map<HttpMethod, Object> stubRequestBody;

        static{
            CommentDto.Post postDto = CommentDto.Post.builder()
                    .content("this is content1")
                    .build();

            CommentDto.Patch patchDto = CommentDto.Patch.builder()
                    .content("this is content1")
                    .build();

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, postDto);
            stubRequestBody.put(HttpMethod.PATCH, patchDto);
        }
        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static CommentDto.Response getSingleResponseBody() {
            return CommentDto.Response.builder()
                    .commentId(1L)
                    .content("this is content1")
                    .boardId(1L)
                    .createdAt(LocalDateTime.now())
                    .nickName("nickName1")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
                    .build();
        }


        public static List<CommentDto.Response> getMultiResponseBody() {


            CommentDto.Response responseDto1 = CommentDto.Response.builder()
                    .commentId(1L)
                    .content("this is content1")
                    .boardId(1L)
                    .createdAt(LocalDateTime.now())
                    .nickName("nickName1")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")                    .build();

            CommentDto.Response responseDto2 = CommentDto.Response.builder()
                    .commentId(2L)
                    .content("this is content2")
                    .boardId(1L)
                    .createdAt(LocalDateTime.now())
                    .nickName("nickName2")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_02.png")
                    .build();

            CommentDto.Response responseDto3 = CommentDto.Response.builder()
                    .commentId(3L)
                    .content("this is content3")
                    .boardId(1L)
                    .createdAt(LocalDateTime.now())
                    .nickName("nickName3")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_03.jpg")
                    .build();



            return List.of(responseDto1,responseDto2,responseDto3);
        }

        public static Comment getSingleResultComment() {

            Comment comment = Comment.builder()
                    .commentId(1L)
                    .content("this is content1")
                    .build();

            return comment;
        }

        public static Page<Comment> getMultiResultComment() {

            Comment comment1 = Comment.builder()
                    .commentId(1L)
                    .content("this is content1")
                    .build();

            Comment comment2 = Comment.builder()
                    .commentId(1L)
                    .content("this is content1")
                    .build();

            Comment comment3 = Comment.builder()
                    .commentId(1L)
                    .content("this is content1")
                    .build();

            return new PageImpl<>(List.of(comment1, comment2,comment3),
                    PageRequest.of(0, 10, Sort.by("commentId").descending()), 3);
        }

        public static Comment getSingleResultComment(long commentId) {

            Comment comment = Comment.builder()
                    .commentId(commentId)
                    .content("this is content1")
                    .build();

            return comment;
        }
    }


    public static class MockCarePost{
        private static List<String> imgUrls = Arrays.asList(
                "https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_01.png",
                "https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_02.png",
                "https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_03.jpg"
        );

        private static final Map<HttpMethod, Object> stubRequestBody;

        private static final Map<String,Integer> stubStartDate;
        private static final Map<String,Integer> stubEndDate;


        static {

            stubStartDate = new HashMap<>();
            stubStartDate.put("day", 13);
            stubStartDate.put("month", 7);
            stubStartDate.put("year", 2023);

            stubEndDate = new HashMap<>();
            stubEndDate.put("day", 20);
            stubEndDate.put("month", 7);
            stubEndDate.put("year", 2023);


            CarePostDto.Post postDto = CarePostDto.Post.builder()
                    .userId(1L)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(imgUrls)
                    .startDate(stubStartDate)
                    .endDate(stubEndDate)
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 노원구")
                    .petSize("중형")
                    .memo("특이사항 없습니다.")
                    .build();

            CarePostDto.Patch patchDto = CarePostDto.Patch.builder()
                    .userId(1L)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(imgUrls)
                    .startDate(stubStartDate)
                    .endDate(stubEndDate)
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 노원구")
                    .petSize("중형")
                    .memo("특이사항 없습니다.")
                    .build();

            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, postDto
            );
            stubRequestBody.put(HttpMethod.PATCH, patchDto);
        }
        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static Map<String, Integer> getStubStartDate() {
            return stubStartDate;
        }
        public static Map<String, Integer> getStubEndDate() {
            return stubEndDate;
        }

        public static CarePostDto.Response getSingleResponseBody() {
            return CarePostDto.Response.builder()
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_01.png"))
                    .nickName("nickName1")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
                    .startDate(stubStartDate)
                    .endDate(stubEndDate)
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 노원구")
                    .petSize("중형")
                    .memo("삶은 소고기를 못 먹어요.")
                    .build();
        }

        public static CarePostDto.Response getSingleResponseTwoBody() {

            return CarePostDto.Response.builder()
                    .title("this is title2")
                    .content("this is content2")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_02.png"))
                    .nickName("nickName2")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_02.png")
                    .startDate(stubStartDate)
                    .endDate(stubEndDate)
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 성북구")
                    .petSize("소형")
                    .memo("특이사항 없습니다.")
                    .build();
        }

        public static CarePostDto.Detail getSingleDetailResponseBody() {
            return CarePostDto.Detail.builder()
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_01.png"))
                    .nickName("nickName1")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
                    .startDate(stubStartDate)
                    .endDate(stubEndDate)
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 노원구")
                    .petSize("중형")
                    .build();
        }

        public static List<CarePostDto.Response> getMultiResponseBody() {

            CarePostDto.Response responseDto1 =
                    CarePostDto.Response.builder()
                            .title("this is title1")
                            .content("this is content1")
                            .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_01.png"))
                            .nickName("nickName1")
                            .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
                            .startDate(stubStartDate)
                            .endDate(stubEndDate)
                            .conditionTag("펫시터예요")
                            .locationTag("서울시 노원구")
                            .petSize("중형")
                            .memo("삶은 소고기를 못 먹어요.")
                            .build();

            CarePostDto.Response responseDto2 =
                    CarePostDto.Response.builder()
                            .title("this is title2")
                            .content("this is content2")
                            .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_02.png"))
                            .nickName("nickName2")
                            .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_02.png")
                            .startDate(stubStartDate)
                            .endDate(stubEndDate)
                            .conditionTag("펫시터예요")
                            .locationTag("서울시 성북구")
                            .petSize("소형")
                            .memo("특이사항 없습니다.")
                            .build();

            CarePostDto.Response responseDto3 =
                    CarePostDto.Response.builder()
                            .title("this is title3")
                            .content("this is content3")
                            .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_03.png"))
                            .nickName("nickName3")
                            .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_03.png")
                            .startDate(stubStartDate)
                            .endDate(stubEndDate)
                            .conditionTag("펫시터예요")
                            .locationTag("서울시 노원구")
                            .petSize("소형")
                            .memo("모르는 사람 경계가 심해요.")
                            .build();

            return List.of(responseDto1,responseDto2,responseDto3);
        }

        public static CarePost getSingleResultCarePost() {

            CarePost carePost = CarePost.builder()
                    .postId(1)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_01.png"))
                    .memo("특이사항 없습니다.")
                    .petSize("소형")
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 노원구")
                    .startDay(stubStartDate.get("day"))
                    .startMonth(stubStartDate.get("month"))
                    .startYear(stubStartDate.get("year"))
                    .endDay(stubEndDate.get("day"))
                    .endMonth(stubEndDate.get("month"))
                    .endYear(stubEndDate.get("year"))
                    .build();

            return carePost;
        }

        public static CarePost getSingleResultCarePost(long postId) {


            CarePost carePost = CarePost.builder()
                    .postId(postId)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_01.png"))
                    .memo("특이사항 없습니다.")
                    .petSize("소형")
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 노원구")
                    .startDay(stubStartDate.get("day"))
                    .startMonth(stubStartDate.get("month"))
                    .startYear(stubStartDate.get("year"))
                    .endDay(stubEndDate.get("day"))
                    .endMonth(stubEndDate.get("month"))
                    .endYear(stubEndDate.get("year"))
                    .build();

            return carePost;
        }

        public static Page<CarePost> getMultiResultBoard() {

            CarePost carePost1 = CarePost.builder()
                    .postId(1)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_01.png"))
                    .memo("특이사항 없습니다.")
                    .petSize("소형")
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 노원구")
                    .startDay(stubStartDate.get("day"))
                    .startMonth(stubStartDate.get("month"))
                    .startYear(stubStartDate.get("year"))
                    .endDay(stubEndDate.get("day"))
                    .endMonth(stubEndDate.get("month"))
                    .endYear(stubEndDate.get("year"))
                    .build();

            CarePost carePost2 = CarePost.builder()
                    .postId(2)
                    .title("this is title2")
                    .content("this is content2")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/careposts/{yyyy-mm-dd}-randomUUID_02.png"))
                    .memo("특이사항 없습니다.")
                    .petSize("대형")
                    .conditionTag("펫시터예요")
                    .locationTag("서울시 강남구")
                    .startDay(stubStartDate.get("day"))
                    .startMonth(stubStartDate.get("month"))
                    .startYear(stubStartDate.get("year"))
                    .endDay(stubEndDate.get("day"))
                    .endMonth(stubEndDate.get("month"))
                    .endYear(stubEndDate.get("year"))
                    .build();

            return new PageImpl<>(List.of(carePost1, carePost2),
                    PageRequest.of(0, 10, Sort.by("postId").descending()),
                    2);
        }



    }

    public static class MockBoard {
        private static Map<HttpMethod, Object> stubRequestBody;

        private static List<String> imgUrls = Arrays.asList("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png",
                "https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_02.png",
                "https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_03.jpg");

        static {

            stubRequestBody = new HashMap<>();
            BoardDto.Post postDto = BoardDto.Post.builder()
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(imgUrls)
                    .build();

            BoardDto.Patch patchDto = BoardDto.Patch.builder()
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(imgUrls)
                    .build();

            stubRequestBody.put(HttpMethod.POST, postDto
            );

            stubRequestBody.put(HttpMethod.PATCH, patchDto);
        }
        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static BoardDto.Response getSingleResponseBody() {
            return BoardDto.Response.builder()
                    .boardId(1)
                    .title("this is title1")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
                    .nickName("nickName1")
                    .likes(0)
                    .checkLike(false)
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png"))
                    .build();
        }

        public static BoardDto.Detail getSingleDetailResponseBody() {

            return BoardDto.Detail.builder()
                    .boardId(1)
                    .title("this is title1")
                    .content("this is content1")
                    .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
                    .nickName("nickName1")
                    .createdAt(LocalDateTime.now())
                    .likes(0)
                    .checkLike(false)
                    .comments(new ArrayList<>())
                    .commentCount(0)
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png"))
                    .build();
        }

        public static List<BoardDto.Response> getMultiResponseBody() {

            BoardDto.Response responseDto1 =
                    BoardDto.Response.builder()
                            .boardId(1)
                            .title("this is title1")
                            .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
                            .nickName("nickName1")
                            .createdAt(LocalDateTime.now())
                            .likes(0)
                            .checkLike(false)
                            .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png"))
                            .build();
            BoardDto.Response responseDto2 =
                    BoardDto.Response.builder()
                            .boardId(2)
                            .title("this is title2")
                            .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_02.png")
                            .nickName("nickName2")
                            .createdAt(LocalDateTime.now())
                            .likes(0)
                            .checkLike(false)
                            .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_02.png"))
                            .build();
            BoardDto.Response responseDto3 =
                    BoardDto.Response.builder()
                            .boardId(3)
                            .title("this is title3")
                            .profileImgUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_03.png")
                            .nickName("nickName3")
                            .createdAt(LocalDateTime.now())
                            .likes(0)
                            .checkLike(false)
                            .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_03.png"))
                            .build();
            return List.of(responseDto1,responseDto2,responseDto3);
        }

        public static Board getSingleResultBoard() {

            List<Comment> comments = new ArrayList<>();

            Board board = Board.builder()
                    .boardId(1)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png"))
                    .likes(0)
                    .likedUserIds(new ArrayList<>())
                    .comments(comments)
                    .commentCount(0)
                    .build();

            return board;
        }

        public static Page<Board> getMultiResultBoard() {

            List<Comment> comments1 = new ArrayList<>();
            List<Comment> comments2 = new ArrayList<>();
            List<Comment> comments3 = new ArrayList<>();


            Board board1 = Board.builder()
                    .boardId(1)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png"))
                    .likes(0)
                    .likedUserIds(new ArrayList<>())
                    .comments(comments1)
                    .commentCount(0)
                    .build();

            Board board2 = Board.builder()
                    .boardId(2)
                    .title("this is title2")
                    .content("this is content2")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_02.png"))
                    .likes(0)
                    .likedUserIds(new ArrayList<>())
                    .comments(comments2)
                    .commentCount(0)
                    .build();

            Board board3 = Board.builder()
                    .boardId(3)
                    .title("this is title3")
                    .content("this is content3")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_03.jpg"))
                    .likes(0)
                    .likedUserIds(new ArrayList<>())
                    .comments(comments3)
                    .commentCount(0)
                    .build();

            return new PageImpl<>(List.of(board1, board2,board3),
                    PageRequest.of(0, 10, Sort.by("boardId").descending()), 3);
        }

        public static Board getSingleResultBoard(long boardId) {

            List<Comment> comments = new ArrayList<>();

            Board board = Board.builder()
                    .boardId(boardId)
                    .title("this is title1")
                    .content("this is content1")
                    .imgUrls(List.of("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png"))
                    .likes(0)
                    .likedUserIds(new ArrayList<>())
                    .comments(comments)
                    .commentCount(0)
                    .build();

            return board;
        }

        public static List<String> getRandomImageUrls(){

           return Arrays.asList("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png"
                    ,"https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_02.png"
                    ,"https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_03.jpg",
                    "https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_04.jpg");
        }
    }

    public static class MockS3 {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            String name = "files";
            String uploadTo = "boards";
            String contentType = "multipart/form-data";
            String path = "src/main/resources/static/temp";
            String originalFileName01 = "1.png";
            String originalFileName02 = "2.png";
            String originalFileName03 = "3.jpg";

            MockMultipartFile multipartFile01 = new MockMultipartFile(
                    name,
                    originalFileName01,
                    contentType,
                    (path + "/" + originalFileName01).getBytes()
            );

            MockMultipartFile multipartFile02 = new MockMultipartFile(
                    name,
                    originalFileName02,
                    contentType,
                    (path + "/" + originalFileName02).getBytes()
            );

            MockMultipartFile multipartFile03 = new MockMultipartFile(
                    name,
                    originalFileName03,
                    contentType,
                    (path + "/" + originalFileName03).getBytes()
            );

            MockMultipartHttpServletRequestBuilder rrbs = (MockMultipartHttpServletRequestBuilder) RestDocumentationRequestBuilders
                    .multipart("/api/s3/uploads")
                    .file(multipartFile01)
                    .file(multipartFile02)
                    .file(multipartFile03)
                    .param("uploadTo", uploadTo);
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.put("from", Collections.singletonList(getSingleResponseBody().getUploadFilePath()));
            params.put("url", Collections.singletonList(getSingleResponseBody().getUploadFileUrl()));
            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, rrbs);
            stubRequestBody.put(HttpMethod.DELETE, params);
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static S3FileDto getSingleResponseBody() {
            return S3FileDto.builder()
                    .originalFileName("1.png")
                    .uploadFileName("randomUUID_01.png")
                    .uploadFilePath("boards")
                    .uploadFileUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png")
                    .build();
        }

        public static List<S3FileDto> getMultiResponseBody() {
            S3FileDto s3FileDto1 =
                    S3FileDto.builder()
                            .originalFileName("1.png")
                            .uploadFileName("randomUUID_01.png")
                            .uploadFilePath("boards")
                            .uploadFileUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_01.png")
                            .build();
            S3FileDto s3FileDto2 =
                    S3FileDto.builder()
                            .originalFileName("2.png")
                            .uploadFileName("randomUUID_02.png")
                            .uploadFilePath("boards")
                            .uploadFileUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_02.png")
                            .build();
            S3FileDto s3FileDto3 =
                    S3FileDto.builder()
                            .originalFileName("3.jpg")
                            .uploadFileName("randomUUID_03.jpg")
                            .uploadFilePath("boards")
                            .uploadFileUrl("https://s3.{region-name}.amazonaws.com/{bucket-name}/boards/{yyyy-mm-dd}-randomUUID_03.jpg")
                            .build();

            return List.of(
                    s3FileDto1,s3FileDto2,s3FileDto3
            );
        }
    }

    public static class MockUser {
        private static Map<HttpMethod, Object> stubRequestBody;
        static {
            stubRequestBody = new HashMap<>();
            stubRequestBody.put(HttpMethod.POST, new UserPostDto("email@example.com","exPassword",
                    "exNickName"));
            stubRequestBody.put(HttpMethod.PATCH, new UserPatchDto(1, "email@example.com","exPassword", "exNickName","exAddress", "https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png"));
        }

        public static Object getRequestBody(HttpMethod method) {
            return stubRequestBody.get(method);
        }

        public static UserResponseDto getSingleResponseBody() {
            return new UserResponseDto("exNickName", "email@example.com", "exAddress","https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png");
        }

        public static UserResponseDto.UserGetResponseDto getSingleGetResponseBody() {
            return new UserResponseDto.UserGetResponseDto(true,"exNickName", "email@example.com", "exAddress","https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png");
        }


        public static List<UserResponseDto> getMultiResponseBody() {
            return List.of(
                    new UserResponseDto("exNickName1", "email1@example.com", "exAddress1","https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png"),
                    new UserResponseDto("exNickName2", "email2@example.com", "exAddress2","https://s3.{region-name}.amazonaws.com/{bucket-name}/profiles/{yyyy-mm-dd}-randomUUID_01.png")
            );
        }

        public static User getSingleResultUser() {

            List<Long> likedBoardList = new ArrayList<>();
            likedBoardList.add(1L);
            likedBoardList.add(2L);
            likedBoardList.add(3L);

            User user = User.builder()
                    .userId(1L)
                    .nickName("exNickName")
                    .password("exPassword")
                    .email("email@example.com")
                    .address("exAddress")
                    .roles(List.of("USER"))
                    .likedBoardList(likedBoardList)
                    .build();

            return user;
        }

        public static Page<User> getMultiResultUser() {
            User user1 = User.builder()
                    .userId(1L)
                    .nickName("exNickName1")
                    .password("exPassword1")
                    .email("email1@example.com")
                    .address("exAddress1")
                    .build();

            User user2 = User.builder()
                    .userId(2L)
                    .nickName("exNickName2")
                    .password("exPassword2")
                    .email("email2@example.com")
                    .address("exAddress2")
                    .build();


            return new PageImpl<>(List.of(user1, user2),
                    PageRequest.of(0, 10, Sort.by("userId").descending()),
                    2);
        }

        public static User getSingleResultUser(long userId) {

            User user = User.builder()
                    .userId(1L)
                    .nickName("exNickName")
                    .password("exPassword")
                    .email("email@example.com")
                    .address("exAddress")
                    .build();

            return user;
        }

    }


}
