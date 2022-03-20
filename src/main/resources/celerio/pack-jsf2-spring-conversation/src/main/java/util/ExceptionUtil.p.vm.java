## Copyright 2015 JAXIO http://www.jaxio.com
##
## Licensed under the Apache License, Version 2.0 (the "License");
## you may not use this file except in compliance with the License.
## You may obtain a copy of the License at
##
##    http://www.apache.org/licenses/LICENSE-2.0
##
## Unless required by applicable law or agreed to in writing, software
## distributed under the License is distributed on an "AS IS" BASIS,
## WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
## See the License for the specific language governing permissions and
## limitations under the License.
##
$output.java($WebUtil, "ExceptionUtil")##

$output.require("javax.persistence.OptimisticLockException")##

$output.dynamicAnnotationTakeOver("javax.inject.Named","javax.inject.Singleton")##
public class $output.currentClass {

    /**
     * Use it during development as an action to throw exceptions and thus verify that your
     * error handling code is correct.
     */
    public void throwRuntimeException() {
        throw new RuntimeException("Just testing RuntimeException from ExceptionUtil ...");
    }
    
    public void throwDataAccessException() {
        throw new OptimisticLockException("Just testing OptimisticLockException from ExceptionUtil ...");
    }

    public static boolean isCausedBy(Throwable e, Class<?> cause) {
        Throwable current = e;
        while (current != null) {
            if (cause.isAssignableFrom(current.getClass())) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}