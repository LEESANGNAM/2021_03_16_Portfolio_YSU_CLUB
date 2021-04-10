#오류들 해결법
##1.동아리 신청 내역이 있을경우
![스크린샷 2021-04-10 오후 1 25 55](https://user-images.githubusercontent.com/61412496/114258256-50ba5b80-9a00-11eb-9abe-2b0c3b0e1de4.png)
## 동아리를 삭제하게 되면 오류가 발생한다.
![스크린샷 2021-04-10 오후 1 30 34](https://user-images.githubusercontent.com/61412496/114258379-10a7a880-9a01-11eb-8e64-f83cc768f03c.png)
### 이유는 참조 무결성제약 조건 때문이다.
### 여기에 자세하게 나와있다. 
https://velog.io/@woodyn1002/삽질-로그-Hibernate에서-부모가-둘인-Entity의-한쪽-부모를-지우면-참조-무결성-오류가-발생하는-문제

#### club의 내용을 바꿔주면 오류가 해결된다.
```java
    @OneToMany(mappedBy = "club",cascade = CascadeType.REMOVE)
    private List<JoinClub> joinClubs = new ArrayList<>();
```