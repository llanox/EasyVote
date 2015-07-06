package ua.org.zasadnyy.zvalidations.validations;

import android.content.Context;
import android.widget.EditText;

import ua.org.zasadnyy.zvalidations.Field;
import ua.org.zasadnyy.zvalidations.R;
import ua.org.zasadnyy.zvalidations.ValidationResult;

/**
 * Created by jgabrielgutierrez on 15-07-05.
 */
public class ConfirmField extends BaseValidation{
    private EditText mPreviousTextField;

    private ConfirmField(Context context, EditText mPreviousTextField) {
        super(context);
        this.mPreviousTextField = mPreviousTextField;
    }


    public static Validation build(Context context, EditText mPreviousTextField) {
        return new ConfirmField(context, mPreviousTextField);
    }


    @Override
    public ValidationResult validate(Field field) {

        EditText textView = field.getTextView();

        ValidationResult result =  NotEmpty.build(this.mContext).validate(field);
        if(!result.isValid()) return result;

        result =  NotEmpty.build(this.mContext).validate(Field.using(mPreviousTextField));
        if(!result.isValid()) return result;


        boolean areEquals = textView.getText().toString().compareTo(mPreviousTextField.getText().toString()) == 0 ? true:false;

        return areEquals ?
                ValidationResult.buildSuccess(textView)
                : ValidationResult.buildFailed(textView, mContext.getString(R.string.zvalidations_confirm_field));

    }
}
