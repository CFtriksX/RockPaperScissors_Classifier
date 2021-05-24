using System;
using System.IO;

using Xamarin.Forms;
using SkiaSharp;
using SkiaSharp.Views.Forms;


namespace ImageInpaintingApp
{
    public partial class PhotoCroppingPage : ContentPage
    {
        PhotoCropperCanvasView photoCropper;
        SKBitmap croppedBitmap;
        SKBitmap scaledBitmap;
        Image mainCropped;

        public PhotoCroppingPage(Image cropped, SKBitmap bitmap)
        {
            InitializeComponent();

            mainCropped = cropped;
            photoCropper = new PhotoCropperCanvasView(bitmap);
            canvasViewHost.Children.Add(photoCropper);
        }
        void OnDoneButtonClicked(object sender, EventArgs args)
        {
            croppedBitmap = photoCropper.CroppedBitmap;
            scaledBitmap = new SKBitmap(240, 240);
            croppedBitmap.ScalePixels(scaledBitmap, SKFilterQuality.High);
            SKColor color = new SKColor(255, 255, 255);
            for (int col = 100; col != 140; col++)
            {
                for (int row = 100; row != 140; row++)
                {
                    scaledBitmap.SetPixel(col, row, color);
                }
            }
            mainCropped.Source = ImageSource.FromStream(() => new MemoryStream(scaledBitmap.Bytes, 0, scaledBitmap.ByteCount));
            Navigation.PopAsync();
        }

        void OnCanvasViewPaintSurface(object sender, SKPaintSurfaceEventArgs args)
        {
            SKImageInfo info = args.Info;
            SKSurface surface = args.Surface;
            SKCanvas canvas = surface.Canvas;

            canvas.Clear();
            canvas.DrawBitmap(scaledBitmap, info.Rect, BitmapStretch.Uniform);
        }
    }
}
