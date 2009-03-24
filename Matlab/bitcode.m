function bitcode(a,b,w,x0,y0)
% sample call:
% bitcode(50,50,0.05,50,100)

height = 150;
width = height;
z = zeros(height,width);

a2 = a^2;
b2 = b^2;
for i=1:height
    for j=1:width
        z(i,j) = g(i,j,a2,b2,x0,y0)*reS(j,x0,w);
        %sum = sum + z(i,j);
    end
end

[x,y] = meshgrid(0:width-1,0:height-1);
% mesh
surface(x,y,z);
xlabel('x')
ylabel('y')
zlabel('G(x,y)')
axis([0 150 0 150 -1 1])
view(0,0)
    
grid minor
%view(2)
%axis equal
%colorbar;

%title(['Gabor wavelet:', ' \alpha=',num2str(a),  ' \beta=', num2str(b), ' \omega=', num2str(w), ' x_0=', num2str(x0), ' y_0=', num2str(y0) ])

%b = sum;
%imshow(z)

function g = g(x,y,a2,b2,x0,y0)
g = exp(-pi*((x-x0)^2/a2 + (y-y0)^2/b2));    

function reS = reS(x,x0,w)
reS = cos(2*pi*w*(x0+x));

function imS = imS(x,x0,w)
imS = sin(2*pi*w*(x0 + x));