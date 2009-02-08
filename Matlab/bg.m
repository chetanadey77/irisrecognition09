function bits = bg( img )
%BG Summary of this function goes here
%   Detailed explanation goes here
bits = zeros(1,2048);
[height,width] = size(img);

i=1;
for a = 50
    for b = 100
        for w = 0.1:0.01:0.21
            for x0=0:width/7:width
                for y0=0:height/7:height
                    bits(i) = bitcode(img,a,b,w,x0,y0)>0;
                    i = i+1
                end
            end
        end
    end
end

