/*
 * WeaselList Console. Copyright (c) 2020.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.alkon.weasellistconsole.application;

import java.util.HashMap;
import java.util.Map;

import static com.alkon.weasellistconsole.application.Utils.isEmpty;

public class ApplicationContext {

    public static final String MONGO_WRAPPER = "mongoWrapper";
    public static final String USER = "user";
    public static final String EXIT_ERROR = "exitError";

    private Map<String, Object> params;

    public ApplicationContext() {
        this.params = new HashMap<>();
    }

    public void setParam(String key, Object value) {
        this.params.put(key, value);
    }

    public Object getParam(String key) {
        if (isEmpty(key)) {
            return null;
        }
        return this.params.get(key);
    }

    public boolean hasParam(String key) {
        return this.getParam(key) != null;
    }

}
