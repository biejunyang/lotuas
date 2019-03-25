import com.lotuas.feign.service.FeignHelloService;
import feign.Feign;
import org.junit.Test;

public class FeignTest {

    @Test
    public void FeignHelloTest(){
        FeignHelloService helloService= Feign.builder().target(FeignHelloService.class, "http://localhost:8080/");
        System.out.println(helloService.syaHello("张三"));
    }
}