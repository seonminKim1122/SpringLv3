# SpringLv1

### API 명세서
|URI|HTTP METHOD|Request|Response|
|---|---|---|---|
|/create|POST|body : {"title":String , "name":String, "content":String, "password":String}|{"title":String, "name":String, "content":String, "modifiedAt":LocalDate}|
|/list|GET|no request body|[{"title":String, "name":String, "content":String, "modifiedAt":LocalDate}, {}, ...]|
|/{id} @PathVariable|GET|no request body|{"title":String, "name":String, "content":String, "modifiedAt":LocalDate}|
|/{id} @PathVariable|PUT|{"title":String, "name":String, "content":String, "password":String}|{"title":String, "name":String, "content":String, "modifiedAt":LocalDate}|
|/{id} @PathVariable|DELETE|{"password":String}|String msg|



#### Q&A
Q1. 수정 삭제 API의 request를 어떤 방식으로 사용하셨나요?
A1. 수정 삭제 대상을 가리키는 id 는 param 방식, 그 외에 필요한 데이터는 body 방식을 사용했습니다.


Q2. 어떤 상황에서 어떤 방식의 request를 써야하나요?
A1. 조건에 대한 필터링(?)을 하고 싶을 때 param 방식이나 query 방식을 사용하면 좋을 것 같습니다. param 방식은 id와 같이 인덱스 역할을 하는 값을 통해 필터링 하려할 때 적합한 것 같고, query 방식은 특정 값으로 필터링 하려할 때 적합한 것 같습니다. body 방식은 서버에 데이터를 보내야 하는 경우에 사용하면 좋은 것 같습니다.(ex : POST, PUT)


Q3. Restful한 API를 설계했나요 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?
A3. 
잘못한 점
1. URI 를 통해 자원을 명시했어야 하는데 게시판 기능밖에 없다고 이를 간과했습니다.


Q4. 적절한 관심사 분리를 적용하였나요?(Controller, Repository, Service)
A4. 수업에서 배운대로 잘 적용한 것 같습니다.


Q5. API 명세서 작성 가이드라인을 검색하여 직접 작성한 API 명세서와 비교해보세요!
A4. API 명세서에 해당 API 에 대한 설명(무슨 역할을 하는지)을 작성해야 하는데 그렇지 않았습니다. 명세서를 작성하는 목적이 다른 사람이 보고 쉽게 이해할 수 있게 하기 위함이라는 걸 생각하면 매우 큰 실수인 것 같습니다. 다음엔 잘 해야겠네요.
