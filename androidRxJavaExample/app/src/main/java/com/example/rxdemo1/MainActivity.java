package com.example.rxdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

//import java.util.Observable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import static java.lang.Boolean.FALSE;

public class MainActivity extends AppCompatActivity {
    private String greeting = "Hello From RxJava no Lambda";
    private Observable<String> myObservable;
    private TextView textView;
    private Button button;
    private String str = "";
    private Integer a, b;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tvGreeting);
        button = findViewById(R.id.button);
        myObservable = Observable.just(greeting);

        Observable<String> source = Observable.create(emitter -> {
            try {
                emitter.onNext("First");
                emitter.onNext("Second");
                emitter.onNext("Third");
                emitter.onComplete();
            }
            catch (Exception e)
            {
                emitter.onError(e);
            }
        });

        Observable<String> source2 = Observable.just("First", "Second", "Third");

        List<String> list = Arrays.asList("Brad", "Nicole", "Denzel");
        Observable<String> sourceFromIterable = Observable.fromIterable(list);
        ConnectableObservable<String> connectableObservable = sourceFromIterable.publish();


        Observer<String> myObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull String s) {
                textView.setText(s);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        };

        //myObservable.subscribe(myObserver);
        //myObservable.subscribe((s)->textView.setText(s));
        //source.subscribe(s -> {textView.setText(s);});

        //source2.subscribe((s) -> {str += s + "\n";});
        //source2.subscribe(s -> str += s.toString(), Throwable::printStackTrace, () -> {});

        //MultiParameterMethodReferenceExample example = new MultiParameterMethodReferenceExample();
        //source2.subscribe(example::doPrintLine, Throwable::printStackTrace, () -> {});

        //sourceFromIterable.subscribe(s -> str += (s+"Observer 1\n"), Throwable::printStackTrace);     //COLD
        //sourceFromIterable.subscribe(s -> str += (s+"Observer 2\n"), Throwable::printStackTrace);

        //connectableObservable.subscribe(s -> str += (s+"Observer 1\n"), Throwable::printStackTrace);  //HOT
        //connectableObservable.subscribe(s -> str += (s+"Observer 2\n"), Throwable::printStackTrace);
        //connectableObservable.connect();

        //MultiParameterMethodReferenceExample multiParameterMethodReferenceExample = new MultiParameterMethodReferenceExample();
        //Observable.range(0, 10).subscribe(multiParameterMethodReferenceExample::doPrintLine);

        //Observable.error(new Exception("Chrash")).subscribe(s -> str = s.toString(), s -> str = s.toString(), () -> {});

        //a = 0;
        //b = 15;
        //Observable<Integer> source_def = Observable.defer(()->Observable.range(a, b));
        //b = 20;
        //source_def.subscribe(d -> str += d.toString() + "\n");

        //Completable.fromRunnable(() -> str += "Done\n").subscribe(() -> str += "Complete");

        /*Observable<Long> sourceToDispose = Observable.interval(1, TimeUnit.SECONDS);

        Disposable dis;
        dis = sourceToDispose.subscribe((emittedLong) -> {
            str += (emittedLong.toString() + "\n");
            textView.setText(str);
        });*/

        Observable<String> sourceFiltered = Observable.just("One", "Two", "Three", "Four");
        sourceFiltered.filter(e -> e.length() > 3).subscribe(s -> str += (s+"\n"));

        textView.setSingleLine(FALSE);
        textView.setText(str);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("CLICKED!");
                //dis.dispose();

            }
        });


    }



}

class MultiParameterMethodReferenceExample {
    String str = "";

    public void doPrintLine(Integer one)
    {
        str = String.format("%s\n", str + one.toString());
    }

}