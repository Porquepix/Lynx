import core.Core;
import core.game.Request;
import core.game.validation.RequestValidator;
import core.game.validation.RequestValidatorBuilder;
import core.game.validation.Validator;
import core.json.JsonFile;

public class Lynx {
	
	public static void main(String[] args) {
		//Core c = new Core();
		RequestValidator v = new RequestValidatorBuilder().type(Integer.class).range(0, 10).in(17, "hello", 3).build();
		System.out.println(v.validate(new Request()));
	}

}
