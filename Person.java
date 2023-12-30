import javax.validation.constraints.*;

public class Person {

    @NotNull(message = "İsim boş olamaz")
    private String name;

    @Min(value = 18, message = "Yaş 18'den küçük olamaz")
    private int age;

    @Email(message = "Geçerli bir e-posta adresi sağlayın")
    private String email;

    
}
