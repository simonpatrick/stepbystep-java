package io.hedwing.java8samples.designpattern.command;

// BEGIN Open
public class Open implements Action {

    private final Editor editor;

    public Open(Editor editor) {
        this.editor = editor;
    }

    @Override
    public void perform() {
        editor.open();
    }
}
// END Open
