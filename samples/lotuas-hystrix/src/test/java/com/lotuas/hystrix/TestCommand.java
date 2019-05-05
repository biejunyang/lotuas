package com.lotuas.hystrix;


import com.lotuas.hystrix.command.RunCommand;
import org.junit.Test;
import rx.Observable;
import rx.Observer;

public class TestCommand {

    @Test
    public void testExecute(){
        //同步执行
        RunCommand command=new RunCommand("使用execute执行命令");
        System.out.println(command.execute());
    }


    @Test
    public void testQueue() throws InterruptedException {
        //异步执行的
        RunCommand command=new RunCommand("使用queue执行命令");
        System.out.println(command.queue());
        Thread.sleep(100);
    }


    @Test
    public void testObserve() throws InterruptedException {
        //异步执行的
        RunCommand command=new RunCommand("使用observe执行命令");
        System.out.println(command.observe());
        Thread.sleep(100);
    }

    @Test
    public void testToObservable() throws InterruptedException {
        //异步执行的
        RunCommand command=new RunCommand("使用ToObservable执行命令");
        Observable<String> observable=command.toObservable();

        observable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("命令执行完成!!");
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("error");
            }

            @Override
            public void onNext(String s) {
                System.out.println("命令执行结果："+s);
            }
        });
        Thread.sleep(1000);
    }
}

