package praktikum.user;

import io.qameta.allure.Step;
import net.datafaker.Faker;
import praktikum.model.User;

public class UserGenerator {

    Faker faker = new Faker();

    @Step("Генерация уникальных данных пользователя")
    public User getRandom() {
        return new User(
                faker.internet().emailAddress(),
                faker.internet().password(),
                faker.name().fullName());
    }

}
