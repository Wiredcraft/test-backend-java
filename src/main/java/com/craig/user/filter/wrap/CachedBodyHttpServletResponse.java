package com.craig.user.filter.wrap;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import lombok.Getter;
import org.springframework.util.FastByteArrayOutputStream;

/**
 * cache response's body stream, so that the logger can save it
 * this method has only support body from {@link org.springframework.http.converter.HttpMessageConverter} ,
 * others such as：{@link ServletResponse#getWriter()} then called {@link Writer#write(java.lang.String)}，won't be saved
 */
@Getter
public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    private final FastByteArrayOutputStream content = new FastByteArrayOutputStream(1024);

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }


    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new CachedBodyServletOutputStream(getResponse().getOutputStream());
    }

    private class CachedBodyServletOutputStream extends ServletOutputStream {

        private final ServletOutputStream os;

        public CachedBodyServletOutputStream(ServletOutputStream os) {
            this.os = os;
        }

        @Override
        public void write(int b) throws IOException {
            content.write(b);
            os.write(b);
        }

        @Override
        public void write(byte[] b, int off, int len) throws IOException {
            content.write(b, off, len);
            os.write(b, off, len);
        }

        @Override
        public boolean isReady() {
            return this.os.isReady();
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {
            this.os.setWriteListener(writeListener);
        }
    }
}
