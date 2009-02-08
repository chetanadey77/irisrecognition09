function bits = bitgen( img )
%BG Summary of this function goes here
%   Detailed explanation goes here
bits = zeros(1,2048);
[height,width] = size(img);

i=1;
for a = 100
    a2 = a^2;
    for b = 200
        b2 = b^2;
        for w = 0.1:0.01:0.21
            for x0=0:width/7:width
                for y0=0:height/7:height
                    sum = 0;
                    for x=1:height
                        for y=1:width
                            sum = sum + double(img(x,y))*exp(-pi*((x-x0)^2/a2 + (y-y0)^2/b2)) * cos(2*pi*w*(x+y)); 
                        end
                    end
                    bits(i) = sum>0;
                    i = i+1;
                end
            end
        end
    end
end
