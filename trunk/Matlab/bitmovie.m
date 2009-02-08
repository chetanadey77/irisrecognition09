function  bitmovie( img )
%BITMOVIE Summary of this function goes here
%   Detailed explanation goes here
[height,width] = size(img);
z = zeros(height,width);
subplot(3,1,1);
imshow(img);
aviobj = avifile('example1.avi');

% constants
aK = 50.0:50.0;
bK = 50.0:50.0;
wK = 0.01:0.01:0.05;
x0K = 0.0:double(width)/7:double(width);
y0K = 0.0:double(height)/7:double(height);

bits = zeros(1,length(aK)*length(bK)*length(wK)*length(x0K)*length(y0K));
i = 1;
for a = aK
    a2 = a^2;
    for b = bK
        b2 = b^2;
        for w = wK
            for x0=x0K
                for y0=y0K
                    sum = 0;
                    for x=1:width
                        for y=1:height
                            z(y,x) = double(img(y,x))*exp(-pi*((x-x0)^2/a2 + (y-y0)^2/b2)) * cos(2*pi*w*(x+y)); 
                            sum = sum + z(y,x);
                        end
                    end
                    subplot(3,1,2);
                    
                    imshow(z,[-160,160]);
          
                    subplot(3,1,3);
                    bits(1,i) = sum > 0;
                    i = i+1;
                    plotbits(bits,width/4);
                    pause(0.001);
                    frame = getFrame(gcf);
                    aviobj = addframe(aviobj,frame);
                end
            end
        end
    end
end
aviobj = close(aviobj);