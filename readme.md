### How to use bou's spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
  - 認証情報：testuser　123456
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### 完成したこと
  - Controllerに対して、Junit機能を用いて、テストを行った
    - 全パターンを網羅してない
    - Serviceも同じ方法で実現できるが、省略した。（Repositoryをモック）
  - Controllerにのメソッドの構造を変えた
    - 戻り値の型を変更
    - 少しの分岐を追加
  - Spring bootのセキュリティ機能を配布
    - 指定したいパスのみアクセス可能
    - 認証機能を追加（id:testuser　pwd:123456）
  - cache ロジックを追加
    - DBへの再検索はいらなくなる
    - 更新、削除、保存処理なども適用した
  - ControllerとServiceに対して、適宜のコメントを補足
  - いくつのバグを対応した
    - 存在しないemployeeの検索制御処理を追加、追加前エラーになる
    - 画面から入力した値がテーブルのフィールドの型と長さなどが違うときにエラーになるの対応
  - バリエーションチェックを追加した

#### Restrictions
- use java 8



#### My experience in Java

- I have about 10 years experience in Java
- I have about 5 years experience in Spring MVC
- I have about 1 years experience in Spring Boot
- I do not know Spring Boot so much but i can understand it very soon,
  case i have long Spring experience and i think Spring boot is just like a tool to help me develop something
