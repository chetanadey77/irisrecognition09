function bitcode(img,a,b,w,x0,y0)
% sample call:
% b = bitcode(img,100,200,0.1,0,0)

[height,width] = size(img);
z = zeros(height,width);
sum = 0;
a2 = a^2;
b2 = b^2;
for i=1:height
    for j=1:width
        z(i,j) = g(i,j,a2,b2,x0,y0)*reS(i,j,w);
        %sum = sum + z(i,j);
    end
end

[x,y] = meshgrid(0:width-1,0:height-1);
surf(x,y,z);

%b = sum;
imshow(z)

function g = g(x,y,a2,b2,x0,y0)
g = exp(-pi*((x-x0)^2/a2 + (y-y0)^2/b2));    

function reS = reS(x,y,w)
reS = cos(2*pi*w*(x+y));

function imS = imS(x,y,w)
imS = sin(2*pi*w*(x+y));