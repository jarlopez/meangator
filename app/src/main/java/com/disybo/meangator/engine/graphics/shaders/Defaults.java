package com.disybo.meangator.engine.graphics.shaders;

import android.support.annotation.NonNull;

public class Defaults {
    private static final String ENDL = ";\n";
    private static final String EQ = " = ";
    private static final String MULT = " * ";

    public static final String DEFAULT_VERTEX_SHADER =
            "uniform mat4 " + Uniform.MVP + ENDL
                    + "attribute vec4 " + Attribute.Position + ENDL
                    + "attribute vec4 " + Attribute.Color + ENDL

                    + "varying vec4 " + Varying.Color + ENDL

                    + "void main()                                                  \n"
                    + "{                                                            \n"
                    + Varying.Color + EQ + Attribute.Color + ENDL
                    + "gl_Position" + EQ + Uniform.MVP + MULT + Attribute.Position + ENDL
                    + "}                              \n";

    public static final String DEFAULT_FRAGMENT_SHADER =
            "precision mediump float;                                               \n"
                    + "varying vec4 " + Varying.Color + ENDL
                    + "void main()                                                  \n"
                    + "{                                                            \n"
                    + "   gl_FragColor " + EQ + Varying.Color + ENDL
                    + "}                                                            \n";


    public static final String DEFAULT_TEX_VERTEX_SHADER =
            "uniform mat4 " + Uniform.MVP + ENDL
                    + "attribute vec4 " + Attribute.Position + ENDL
                    + "attribute vec4 " + Attribute.Color + ENDL
                    + "attribute vec2 " + Attribute.TextureCoordinate + ENDL

                    + "varying vec4 " + Varying.Color + ENDL
                    + "varying vec2 " + Varying.TextureCoordinate + ENDL

                    + "void main()                                                  \n"
                    + "{                                                            \n"
                    + Varying.Color + EQ + Attribute.Color + ENDL
                    + Varying.TextureCoordinate + EQ + Attribute.TextureCoordinate + ENDL
                    + "gl_Position" + EQ + Uniform.MVP + MULT + Attribute.Position + ENDL
                    + "}                              \n";

    public static final String DEFAULT_TEX_FRAGMENT_SHADER =
            "precision mediump float;                                               \n"
                    + "uniform sampler2D " + Uniform.Texture + ENDL

                    + "varying vec4 " + Varying.Color + ENDL
                    + "varying vec2 " + Varying.TextureCoordinate + ENDL

                    + "void main()                                                  \n"
                    + "{                                                            \n"
                    + "   gl_FragColor " + EQ + Varying.Color + MULT
                    + "texture2D("
                    + Uniform.Texture + ", "
                    + Varying.TextureCoordinate
                    + ")" + ENDL
                    + "}                                                            \n";


    interface IndexedField {
        int UNASSIGNED = -1;

        int index();

        String repr();
    }

    public enum Attribute implements IndexedField {
        Position(0, "Position"),
        Color(1, "Color"),
        TextureCoordinate(2, "TexCoordinate");

        private static final String PREP = "a_";
        private int index;
        private String repr;

        Attribute(int index, String repr) {
            this.index = index;
            this.repr = PREP + repr;
        }

        @Override
        public int index() {
            return index;
        }

        @Override
        public String repr() {
            return repr;
        }

        @NonNull
        @Override
        public String toString() {
            return repr();
        }
    }

    public enum Uniform implements IndexedField {
        MVP("MVP"),
        Texture("Texture");

        private static final String PREP = "u_";

        private int index;
        private String repr;

        Uniform(String repr) {
            this(UNASSIGNED, repr);
        }

        Uniform(int index, String repr) {
            this.index = index;
            this.repr = PREP + repr;
        }

        @Override
        public int index() {
            return index;
        }

        @Override
        public String repr() {
            return repr;
        }

        @NonNull
        @Override
        public String toString() {
            return repr();
        }
    }

    public enum Varying implements IndexedField {
        Color("Color"),
        TextureCoordinate("TexCoordinate");

        private static final String PREP = "v_";

        private int index;
        private String repr;

        Varying(String repr) {
            this(UNASSIGNED, repr);
        }

        Varying(int index, String repr) {
            this.index = index;
            this.repr = PREP + repr;
        }

        @Override
        public int index() {
            return index;
        }

        @Override
        public String repr() {
            return repr;
        }

        @NonNull
        @Override
        public String toString() {
            return repr();
        }
    }
}
