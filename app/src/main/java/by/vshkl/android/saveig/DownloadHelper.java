package by.vshkl.android.saveig;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

class DownloadHelper {

    static Observable<String> getImageUrl(final String imagePageUrl) {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                String imageUrl = extractImageUrl(imagePageUrl);
                emitter.onNext(imageUrl);
            }
        });
    }

    private static String extractImageUrl(final String imagePageUrl) throws IOException {
        Document document = Jsoup.connect(imagePageUrl).get();
        Element metaImage = document.select(Constants.IMAGE_META).first();
        return metaImage != null ? metaImage.attr(Constants.ATTR_CONTENT) : "";
    }
}
