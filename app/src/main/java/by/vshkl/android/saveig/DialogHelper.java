package by.vshkl.android.saveig;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Html;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;

import permissions.dispatcher.PermissionRequest;

class DialogHelper {

    static void showWriteExternalStorageRationaleDialog(final Context context,
                                                        final PermissionRequest request) {
        new MaterialStyledDialog.Builder(context)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setTitle(R.string.permission_rationale_title)
                .setDescription(R.string.permission_rationale_message)
                .setHeaderColor(R.color.colorPrimary)
                .setPositiveText(R.string.permission_rationale_allow)
                .setNegativeText(R.string.permission_rationale_deny)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.proceed();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        request.cancel();
                    }
                })
                .build()
                .show();
    }

    static void showHowToDialog(final Context context) {
        new MaterialStyledDialog.Builder(context)
                .setStyle(Style.HEADER_WITH_TITLE)
                .setTitle(R.string.how_to_title)
                .setDescription(R.string.how_to_message)
                .setPositiveText(R.string.how_to_close)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.hide();
                    }
                })
                .build()
                .show();
    }
}
