## #{}与${}的区别
1. **在MyBatis等持久层框架中的区别**
    - **`#{}`语法（预编译处理）**
        - **安全性和防SQL注入**：`#{}`在MyBatis等框架中是一种参数占位符，它会对传入的参数进行预编译处理。在SQL语句执行过程中，MyBatis会将`#{}`替换为`?`，然后使用PreparedStatement来设置参数值。这种方式可以有效地防止SQL注入攻击。例如，有一个查询用户的SQL语句`SELECT * FROM users WHERE username = #{username}`，当传入`username`参数时，MyBatis会将其作为一个普通的值进行处理，即使传入的内容包含SQL关键字或特殊字符，也不会改变SQL语句的语义，因为它是作为一个参数值而不是SQL语句的一部分来处理的。
        - **数据类型转换和兼容性**：`#{}`在设置参数时会根据参数的类型进行自动转换。例如，如果在Java代码中传入一个整数类型的参数到SQL语句中的`#{}`占位符，MyBatis会将其正确地转换为SQL中的整数类型进行比较。同时，`#{}`在处理字符串类型的参数时，会自动添加引号。如果在Java中传入一个字符串`"user1"`，在SQL语句中实际会被处理为`'user1'`，这符合SQL语法中字符串的表示方式。
    - **`${}`语法（字符串拼接）**
        - **动态SQL拼接灵活性**：`${}`是一种文本替换方式，它会将传入的参数直接拼接到SQL语句中。这种方式在构建动态SQL时非常有用，例如在动态选择表名、列名或者排序字段等情况。例如，`SELECT * FROM ${tableName}`，可以根据不同的业务逻辑在运行时动态地传入表名，如`"users"`或`"orders"`，从而实现灵活的查询。但是，这种灵活性也带来了安全风险。
        - **SQL注入风险和注意事项**：由于`${}`是直接将参数拼接进SQL语句，所以如果参数是由用户输入或者不可信的来源提供，就很容易引发SQL注入攻击。例如，如果用户输入的`tableName`参数包含SQL关键字，如`"users; DROP TABLE products"`，那么执行的SQL语句就会变成`SELECT * FROM users; DROP TABLE products`，这会导致数据丢失等严重后果。因此，在使用`${}`时，必须确保传入的参数是可信任的，或者在拼接前对参数进行严格的过滤和验证。

2. **在其他编程语言或模板引擎中的类似概念对比（如果适用）**
    - 在一些编程语言的模板引擎中，也有类似的变量替换机制。例如，在Python的Jinja2模板引擎中，`{{ variable }}`用于输出变量的值，它类似于`#{}`的安全输出方式，会对变量进行转义和安全处理。而如果模板引擎中有类似于直接拼接字符串的语法（虽然Jinja2没有这样的不安全语法，但有些其他简单的模板引擎可能有），就类似于`${}`，在使用时需要注意安全风险。这种区别主要是为了在方便模板渲染和保证安全之间找到平衡，与MyBatis中的`#{}`和`${}`的设计理念有相似之处。

## ON DUPLICATE KEY UPDATE
1. **`VALUES()`函数与`NULL`值处理机制**
    - 在`ON DUPLICATE KEY UPDATE`语句中，`VALUES()`函数用于获取试图插入的值。当执行插入操作并且发生唯一键或主键冲突时，它会将`VALUES()`函数中的值用于更新对应的列。
    - 如果试图插入的值为`NULL`，那么在使用`VALUES()`函数进行更新时，对应的列也会被更新为`NULL`。这是因为`VALUES()`函数是按照插入时提供的值来进行更新操作的。

2. **示例说明**
    - **初始数据**：假设我们有一个名为`media_files`的表，包含`user_id`、`video_id`等列，并且`user_id`和`video_id`构成唯一键组合。表中有一条现有记录：
      | user_id | video_id | file_index | file_name | file_size | file_path | update_type | transfer_result | duration |
      | --- | --- | --- | --- | --- | --- | --- | --- | --- |
      | 1 | 1 | 1 | 'file1.mp4' | 1024 | '/path/to/file1' | 'upload' | 'success' | 120 |
    - **插入`NULL`值的操作及结果**：
        - 执行插入语句`INSERT INTO media_files (user_id, video_id, file_index, file_name, file_size, file_path, update_type, transfer_result, duration) VALUES (1, 1, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ON DUPLICATE KEY UPDATE user_id = VALUES(user_id), video_id = VALUES(video_id), file_index = VALUES(file_index), file_name = VALUES(file_name), file_size = VALUES(file_size), file_path = VALUES(file_path), update_type = VALUES(update_type), transfer_result = VALUES(transfer_result), duration = VALUES(duration);`。
        - 由于`user_id`和`video_id`的组合（1,1）已经存在，会触发`ON DUPLICATE KEY UPDATE`操作。更新后的记录将变为：
          | user_id | video_id | file_index | file_name | file_size | file_path | update_type | transfer_result | duration |
          | --- | --- | --- | --- | --- | --- | --- | --- | --- |
          | 1 | 1 | NULL | NULL | NULL | NULL | NULL | NULL | NULL |

3. **注意事项和应用场景考虑**
    - **数据完整性风险**：在使用这种更新方式时，如果不小心将列更新为`NULL`，可能会破坏数据的完整性。例如，如果某个列在业务逻辑中有非`NULL`的要求（如`file_path`列可能在后续的文件读取操作中不能为空），那么这种更新可能会导致后续操作出现问题。
    - **应用场景中的验证逻辑**：在实际应用场景中，可能需要在应用程序层或者数据库的存储过程中添加验证逻辑。例如，可以在插入操作之前，先检查插入的值是否符合业务规则（如某些列不允许为`NULL`），或者在`ON DUPLICATE KEY UPDATE`语句中通过`CASE`语句等方式有条件地更新列，避免将重要列更新为`NULL`。例如：`ON DUPLICATE KEY UPDATE file_path = CASE WHEN VALUES(file_path) IS NOT NULL THEN VALUES(file_path) ELSE file_path END;`这样的语句可以确保`file_path`列只有在插入的值不为`NULL`时才会被更新。

## 一对多查询的例子
VideoCommentMapper.xml
```
<resultMap id="base_result_map_children" type="org.jingtao8a.entity.po.VideoComment" extends="base_result_map">
	<!--将主查询中comment_id列的值传递给名为selectChildComment的子查询-->
	<collection property="children" column="comment_id" select="org.jingtao8a.mapper.VideoCommentMapper.selectChildComment"></collection>
</resultMap>

<select id="selectChildComment" resultMap="base_result_map">
	select <include refid="base_column_list"></include>, u.nick_name nickName, u.avatar avatar, u2.nick_name replyNickName, u2.avatar replyAvatar
	from video_comment v
	inner join user_info u on  u.user_id = v.user_id
	left join user_info u2 on u2.user_id = v.reply_user_id
	where p_comment_id = #{commentId} order by v.comment_id asc
</select>

<select id="selectListWithChildren" resultMap="base_result_map_children">
	select <include refid="base_column_list"></include>, u.nick_name nickName, u.avatar avatar
	from video_comment v left join user_info u on u.user_id = v.user_id
	<include refid="query_condition"></include>
	<if test="query.orderBy != null">
		order by ${query.orderBy}
    </if>
	<if test="query.simplePage != null">
		limit #{query.simplePage.start}, #{query.simplePage.end}
    </if>
</select>
```
## SELECT LAST_INSERT_ID()
单条插入之后，获取已插入主键自增ID的最大值(即最后一个插入自增ID)
```
<selectKey keyProperty="bean.commentId" resultType="Integer" order="AFTER">
    SELECT LAST_INSERT_ID()
</selectKey>
```


