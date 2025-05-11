package hello.hello_spring.repository;

import hello.hello_spring.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{


    private final JdbcTemplate jdbcTemplate;

    //jdbcTemplate 만들기
    //DataSource는 데이터베이스에 연결하기 위해 필요한 정보를 담고 있는 객체
    //스프링이 자동으로 DB 연결 정보를 넣어줌
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource){
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //데이터 저장
    @Override
    public Member save(Member member) {
        //SimpleJdbcInsert 객체 생성
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    //데이터 조회
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id=?", memberRowMapper(),id);
        return result.stream().findAny();

    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name=?", memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        //
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    //자바 객체로 바꾸기
    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            return member;
        };
    }
}
