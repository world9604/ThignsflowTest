package com.taein.thignsflowtest.github.githubIssue;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.taein.thignsflowtest.github.data.entity.GithubIssue;
import com.taein.thignsflowtest.github.data.entity.GithubRepo;
import com.taein.thignsflowtest.github.data.repository.GithubRepository;
import com.taein.thignsflowtest.github.data.vo.GithubRepoWithIssuesVO;
import com.taein.thignsflowtest.github.utils.ErrorHandler;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lombok.Data;

import static com.taein.thignsflowtest.github.githubIssue.GithubIssueListActivity.GITHUB_TAG;

@SuppressLint("CheckResult")
@Data
public class GithubIssueListViewModel extends ViewModel {

    private GithubRepository repository;
    private final String ORG_NAME = "google";
    private MutableLiveData<String> repoName = new MutableLiveData<>();
    private MutableLiveData<GithubIssue> githubIssueLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> clearItemsLiveData = new MutableLiveData<>();

    public GithubIssueListViewModel(GithubRepository repository) {
        this.repository = repository;
    }

    public void onStart() {
        final String repoName = "dagger";

        repository.getGithubRepoWithIssuesFromQuery(ORG_NAME, repoName)
                .subscribeOn(Schedulers.io())
                .doOnError(ErrorHandler.get())
                .doOnNext(githubRepoWithIssuesVOs -> saveGithubRepo(repoName, githubRepoWithIssuesVOs))
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(GithubIssue::create)
                .doOnNext(this::saveGithubIssues)
                .flatMap(r -> repository.getGithubIssues(r.getGithubRepoid())
                        .toObservable()
                        .take(1)
                        .flatMap(Observable::fromIterable))
                .observeOn(AndroidSchedulers.mainThread ())
                .subscribe(githubIssue -> {
//                    Log.d(GITHUB_TAG, "githubIssue : " + githubIssue.getNumber());
                    githubIssueLiveData.setValue(githubIssue);
                });
    }

    private void saveGithubRepo(String repoName, List<GithubRepoWithIssuesVO> githubRepoWithIssuesVOS) {
        GithubRepoWithIssuesVO githubRepoWithIssuesVO = githubRepoWithIssuesVOS.get(0);
        GithubRepo githubRepo = GithubRepo.create(repoName, githubRepoWithIssuesVO);
        repository.insertGithubRepo(githubRepo);
    }

    private void saveGithubIssues(GithubIssue githubIssue) {
        repository.insertGithubIssue(githubIssue);
    }

    public void changeRepo(String repoName) {
        this.repoName.setValue(repoName);

        repository.getGithubRepoWithIssuesFromQuery(ORG_NAME, repoName)
                .subscribeOn(Schedulers.io())
                .doOnError(ErrorHandler.get())
                .doOnNext(githubRepoWithIssuesVOs -> saveGithubRepo(repoName, githubRepoWithIssuesVOs))
                .toObservable()
                .flatMap(Observable::fromIterable)
                .map(GithubIssue::create)
                .doOnNext(this::saveGithubIssues)
                .flatMap(r -> repository.getGithubIssues(r.getGithubRepoid())
                        .toObservable()
                        .take(1)
                        .flatMap(Observable::fromIterable))
                .observeOn(AndroidSchedulers.mainThread ())
                .startWith(Observable.create(emitter -> {
                    // clearItemsLiveData 로 adapter 의 item 을 지운다.
                    // emitter 로 기존 java 로직을 Observable 과 연결 한다.
                    // 이때, 수동으로 emitter.onComplete(); 를 꼭 호출하여 종료 시켜 줘야 함.
                    try {
                        Log.d(GITHUB_TAG, "startWith");
                        clearItemsLiveData.setValue(true);
                    } catch (Exception ex) {
                        emitter.onError(ex);
                    } finally {
                        emitter.onComplete();
                    }
                }))
                .subscribe(githubIssue -> {
                    Log.d(GITHUB_TAG, "githubIssue : " + githubIssue.getNumber());
                    githubIssueLiveData.setValue(githubIssue);
                });
    }
}