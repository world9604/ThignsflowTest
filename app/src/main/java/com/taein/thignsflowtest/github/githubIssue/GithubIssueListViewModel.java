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

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lombok.Data;

import static com.taein.thignsflowtest.github.githubIssue.GithubIssueListActivity.GITHUB_TAG;

@Data
public class GithubIssueListViewModel extends ViewModel {

    private GithubRepository repository;
    private final String ORG_NAME = "google";
    private MutableLiveData<String> repoName = new MutableLiveData<>();
    private MutableLiveData<GithubIssue> githubIssueLiveData = new MutableLiveData<>();

    public GithubIssueListViewModel(GithubRepository repository) {
        this.repository = repository;
    }

    @SuppressLint("CheckResult")
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
                    Log.d(GITHUB_TAG, "githubIssue : " + githubIssue.getNumber());
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

    @SuppressLint("CheckResult")
    public void changeRepo(String repoName) {
        this.repoName.setValue(repoName);

        /*repository.getGithubRepoWithIssuesFromQuery(ORG_NAME, repoName)
                .subscribeOn(Schedulers.io())
                .doOnError(ErrorHandler.get())
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(githubRepoWithIssuesVO -> {
                    Log.d(GITHUB_TAG, "getTitle : " + githubRepoWithIssuesVO.get(0).getNumber());
                    githubIssueLiveData.setValue(githubRepoWithIssuesVO);
                });*/
    }

    @SuppressLint("CheckResult")
    public void gugudanTest(int dan) {
//        Function<Integer, Observable<String>> gugudan = num -> Observable.range(1, 9).map(row -> num + "*" + row + " = " + dan * row);
        /*Observable.just(dan)
                .flatMap(num ->
                    Observable.range(1, 9).map(row -> num + "*" + row + " = " + dan * row))
                .subscribe(System.out::println);*/
    }
}
