package mipt.bit.json;

public class JsonOffsetBuffer {
    char[] buffer;
    int pc = 0;

    public JsonOffsetBuffer(char[] buffer, int pc) {
        this.buffer = buffer;
        this.pc = pc;
    }

    public char getChar() {
        return buffer[pc];
    }

    public char charAt(int index) {
        return buffer[pc + index];
    }

    public boolean isEndOfBuffer() {
        return pc >= buffer.length;
    }

    public void increment() {
        if (pc + 1 < buffer.length) {
            pc++;
        } else {
            pc = buffer.length;
        }
    }

    public void shift(int offset) {
        if (pc + offset < buffer.length) {
            pc += offset;
        } else {
            pc = buffer.length;
        }
    }
}
