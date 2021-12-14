package todo.model;

import todo.role.Role;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Entity
@Table(name = "t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public static User of(String name, String email, String password, Role role) {
        User user = new User();
        user.name = name;
        user.role = role;
        user.email = email;
        try {
            user.password = MD5.createHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = MD5.createHash(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public boolean validatePwd(String enteredPwd){
        try {
            return MD5.validateString(enteredPwd, password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private static class MD5 {

        public static String createHash(String s) throws NoSuchAlgorithmException {
            MessageDigest alg = MessageDigest.getInstance("MD5");
            alg.reset();
            alg.update(s.getBytes());
            byte[] bytes = alg.digest();
            BigInteger nr = new BigInteger(1, bytes);
            return nr.toString(16);
        }

        public static boolean validateString(String str, String hash) throws NoSuchAlgorithmException {
            return hash.equals(createHash(str));
        }
    }
}