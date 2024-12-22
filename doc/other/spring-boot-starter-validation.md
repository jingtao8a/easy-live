## 使用spring-boot-starter-validation进行参数校验
原理：AOP
### 请求参数校验AOP
- @NotEmpty 用于字符串
- @Size 用于字符串
- @NotNull 用于非字符串
- @Max @Min 用于数字

1. **在Web层进行请求验证**
    - **控制器方法参数验证**
        - **基本验证规则应用**：在Spring Boot的Web控制器中，对于接收的请求参数，应该广泛使用`@Valid`或`@Validated`注解来触发验证。例如，对于一个创建用户的POST请求，请求体包含用户信息对象，在控制器方法的参数前添加`@Valid`注解。同时，为了处理验证失败的情况，需要添加`BindingResult`参数紧跟在验证对象之后。
          ```java
          @PostMapping("/users")
          public ResponseEntity<User> createUser(@Valid @RequestBody User user, BindingResult bindingResult) {
              if (bindingResult.hasErrors()) {
                  // 处理验证错误，例如返回错误信息给客户端
                  List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                  // 构建错误响应...
                  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
              }
              // 保存用户信息等操作
              return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
          }
          ```
        - **分组验证**：当一个实体类有多种验证场景时，如创建用户和更新用户可能有不同的验证规则，可以使用验证分组。首先定义接口作为验证分组标记，例如`CreateUserGroup`和`UpdateUserGroup`。然后在实体类的验证注解中指定分组，如`@NotBlank(groups = CreateUserGroup.class)`。在控制器方法中，通过`@Validated`注解并指定分组来触发相应的验证，如`@Validated(CreateUserGroup.class)`。
    - **全局异常处理与验证错误响应统一化**
        - **创建全局异常处理类**：创建一个`@ControllerAdvice`注解的类来处理验证异常。当验证失败时，Spring会抛出`MethodArgumentNotValidException`（对于`@Valid`注解）或`ConstraintViolationException`（对于方法参数或路径变量等其他情况）。在全局异常处理类中捕获这些异常，并返回统一格式的错误响应给客户端。
          ```java
          @ControllerAdvice
          public class GlobalExceptionHandler {
              @ExceptionHandler(MethodArgumentNotValidException.class)
              public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
                  BindingResult bindingResult = ex.getBindingResult();
                  List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                  ErrorResponse errorResponse = new ErrorResponse("参数验证失败");
                  fieldErrors.forEach(fieldError -> errorResponse.addError(fieldError.getField(), fieldError.getDefaultMessage()));
                  return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
              }
          }
          ```
        - **定义错误响应实体类**：`ErrorResponse`类用于封装错误信息，包括错误消息和具体的字段错误信息，这样可以使客户端更容易理解验证错误的原因。

2. **在服务层和数据访问层的应用**
    - **服务层验证逻辑补充**：虽然大部分验证可以在Web层完成，但服务层也可以进行一些验证。例如，对于一些业务规则相关的验证，如验证用户权限是否足够进行某个操作，或者验证两个实体之间的关联关系是否符合业务逻辑。服务层可以调用`Validator`实例来手动进行验证。假设已经通过`@Autowired`注入了`Validator`，对于一个用户修改密码的服务方法，可以这样验证：
      ```java
      @Service
      public class UserService {
          @Autowired
          private Validator validator;
          public void changePassword(User user, String newPassword) {
              Set<ConstraintViolation<User>> violations = validator.validate(user);
              if (!violations.isEmpty()) {
                  // 处理验证错误，如抛出业务异常
                  throw new BusinessValidationException("用户信息验证失败");
              }
              // 执行修改密码操作
          }
      }
      ```
    - **数据访问层与验证结合确保数据质量**：在数据访问层（如使用Spring Data JPA），可以在实体类中定义验证规则，确保存入数据库的数据符合一定的质量标准。当保存或更新实体时，如果实体不符合验证规则，会抛出异常。可以在数据访问层捕获这些异常并进行适当的处理，如回滚事务、记录错误日志等。这样可以保证数据库中的数据完整性和一致性。

3. **自定义验证规则和注解的合理使用**
    - **创建有针对性的自定义验证注解**：当业务中有特殊的验证需求，如验证手机号码、身份证号码等格式，或者验证复杂的业务逻辑（如订单金额是否超过用户信用额度），应该创建自定义验证注解。按照规范创建自定义验证注解和对应的验证器实现类，如前面提到的手机号码验证示例。
    - **维护和管理自定义验证规则**：将自定义验证规则放在一个单独的包或模块中进行管理，便于代码的维护和复用。同时，为自定义验证注解编写清晰的文档，说明其用途、验证逻辑和适用场景，以便其他开发人员能够正确使用。

4. **与前端协作和测试验证逻辑**
    - **前后端协作定义验证规则**：在开发过程中，与前端开发人员紧密协作，确保前后端的验证规则一致。例如，前端可以根据后端定义的验证规则进行初步的表单验证，减少不必要的后端验证请求。后端也可以提供详细的验证错误消息，以便前端能够更好地展示错误提示给用户。
    - **单元测试和集成测试验证逻辑**：编写单元测试来验证验证逻辑的正确性。对于每个包含验证注解的实体类和控制器方法，编写测试方法来模拟请求并验证验证是否按照预期进行。可以使用测试框架如JUnit和Mockito。在集成测试中，测试整个请求 - 验证 - 响应流程，确保在真实的应用场景下验证逻辑能够正确工作，包括全局异常处理是否返回正确的错误响应。