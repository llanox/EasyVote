/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Vitaliy Zasadnyy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package ua.org.zasadnyy.zvalidations;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitaliyzasadnyy on 01.02.14.
 */
public class ToastValidationFailedRenderer implements ValidationFailedRenderer {

    private Context mContext;
    private List<Toast> mToasts = new ArrayList<Toast>();

    public ToastValidationFailedRenderer(Context context) {
        mContext = context;
    }

    @Override
    public void showErrorMessage(ValidationResult validationResult) {
        Toast toast = Toast.makeText(mContext, validationResult.getMessage(), Toast.LENGTH_SHORT);
        mToasts.add(toast);
        toast.show();
    }

    @Override
    public void clear() {
        for (Toast toast : mToasts) {
            toast.cancel();
        }
        mToasts.clear();
    }

}
