package gr.aueb.cf.boardgamelibcf.dto;

public class TokenDTO {
    private Integer status;
    private String token;

    public TokenDTO() {}

    public TokenDTO(Integer status, String token) {
        this.status = status;
        this.token = token;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
