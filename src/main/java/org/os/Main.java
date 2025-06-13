package org.os;


import org.os.core.JetLight;

import java.io.IOException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws IOException, NoSuchMethodException {

        JetLight.runServer("org.os",8080);

        /**
         * TODO
         * final result make this as (framework) to allow user to create
         * fast api with little configuration and add security faster
         * without any complex code to make it suitable for student
         * to understand how frameworks works
         * Handle @Entity try to add simple ORM for crud and relation between entities
         */

        /**
         * DONE ✔
         * add virtual threads for each request
         * add put method to modify the object in the list
         * add security layer, jwt and Ip restriction
         * add annotations
         * add reflection
         */

    }


}