import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.thymeleaf.ThymeleafTemplateEngine;

public class Main {

    public static void main(String[] args) {
        // localhost:4567 adresinde çalışan bir Spark uygulaması başlat
        spark.Spark.port(4567);

        // Ana sayfa
        spark.Spark.get("/", Main::renderForm, new ThymeleafTemplateEngine());

        // Formu işle
        spark.Spark.post("/process-form", Main::processForm, new ThymeleafTemplateEngine());
    }

    private static ModelAndView renderForm(Request request, Response response) {
        return new ModelAndView(null, "form.html");
    }

    private static ModelAndView processForm(Request request, Response response) {
        String name = request.queryParams("name");
        int age = Integer.parseInt(request.queryParams("age"));
        String email = request.queryParams("email");

        // Doğrulama kurallarına tabi olan bir nesne oluştur
        Person person = new Person();
        person.setName(name);
        person.setAge(age);
        person.setEmail(email);

        // Bean Validation'ı başlat
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Nesneyi doğrula
        Set<ConstraintViolation<Person>> violations = validator.validate(person);

        // Hataları kontrol et
        if (!violations.isEmpty()) {
            Map<String, Object> model = new HashMap<>();
            model.put("errors", violations);
            return new ModelAndView(model, "form.html");
        }

        // Doğrulama başarılıysa sonuç sayfasını göster
        Map<String, Object> model = new HashMap<>();
        model.put("person", person);
        return new ModelAndView(model, "result.html");
    }
}
