package model;

public class Member {
    private String name; // 이름
    private String password; // 비밀번호
    private String studentId; // 학번
    private String email; // 이메일

    @Override
    public String toString() {
        return "이름 = " + name +
                "학번 = " + studentId +
                "이메일 = " + email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
