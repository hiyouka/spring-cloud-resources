package hiyouka.reactor.rx.entity;

import lombok.Data;

/**
 * @author hiyouka
 * @since JDK 1.8
 */
@Data
public class User {

    private Integer id;

    private String nick;

    private String email;

    private String phone;

    public User(Integer id, String s) {
        this.id = id;
        this.nick = s;
    }
}