function bits = bitmovie( imname, plotOn )
% bits = bitmovie('unwrapped.jpg',true);
img = imread(imname);

%BITMOVIE Summary of this function goes here
%   Detailed explanation goes here
[height,width] = size(img);
z = zeros(height,width);

if plotOn
    subplot(3,1,1);
    imshow(img);
    title('Unwrapped iris')
    aviobj = avifile('example1.avi');
end

% constants
ab_lowLim = 8;
ab_upLim = 14;
ab_numSteps = 2;

w_lowLim = 0.15;
w_upLim  = 0.1;
w_numSteps = 2;

x0_numSteps = width-ab_lowLim*2;
x0_lowLim = ab_upLim;
x0_upLim = width-ab_upLim;

y0_numSteps = 2;
y0_lowLim = height-ab_lowLim;
y0_upLim =  height-ab_upLim;

aK = ab_lowLim : (ab_upLim - ab_lowLim)/ab_numSteps : ab_upLim;
bK = aK;
wK = w_lowLim : (w_upLim - w_lowLim)/w_numSteps : w_upLim;
x0K = x0_lowLim : (x0_upLim - x0_lowLim)/x0_numSteps : x0_upLim;
y0K = y0_lowLim : (y0_upLim - y0_lowLim)/y0_numSteps : y0_upLim;

bits = zeros(1,length(x0K)*3);
i = 1;
for x0=x0K
    for j=1:3
        y0 = y0K(j);
        a2 = aK(j)^2;
        b2 = a2;
        w = wK(j);

        sum = 0;
        for x=1:width
            for y=1:height
                z(y,x) = double(img(y,x))*exp(-pi*((x-x0)^2/a2 + (y-y0)^2/b2)) * cos(2*pi*w*(x-x0));
                sum = sum + z(y,x);
            end
        end
        bits(1,i) = sum > 0;
        i = i+1;
        if plotOn
            subplot(3,1,2);
            imshow(z,[-160,160]);
            title('Gabor wavelet')
            subplot(3,1,3);
            plotbits(bits,width/6);
            title('Bitcode')
            pause(0.001);
            frame = getFrame(gcf);
            aviobj = addframe(aviobj,frame);
        end
    end

end
if plotOn
    aviobj = close(aviobj);
end