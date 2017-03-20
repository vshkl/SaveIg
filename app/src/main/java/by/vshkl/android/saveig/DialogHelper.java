package by.vshkl.android.saveig;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import permissions.dispatcher.PermissionRequest;

class DialogHelper {

    static void showWriteExternalStorageRationaleDialog(final Context context,
                                                        final PermissionRequest request) {
        new MaterialDialog.Builder(context)
                .title(R.string.permission_rationale_title)
                .content(R.string.permission_rationale_message)
                .positiveText(R.string.permission_rationale_allow)
                .negativeText(R.string.permission_rationale_deny)
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
                .show();
    }
}
